package com.footballer.rest.api.v1.manager;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.filter.AppContextHolder;
import com.footballer.rest.api.v1.dao.EventDao;
import com.footballer.rest.api.v1.domain.FootballPlayerBalance;
import com.footballer.rest.api.v1.domain.FootballPlayerTeamActivity;
import com.footballer.rest.api.v1.vo.Account;
import com.footballer.rest.api.v1.vo.Event;
import com.footballer.rest.api.v1.vo.enumType.FeeType;
import com.footballer.rest.api.v1.vo.enumType.PlayerTeamActivityType;
import com.footballer.rest.api.v2.manager.StatisticsManager;
import com.footballer.util.DateUtil;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

@Service
public class PlayerManager {
	
	Logger logger = LoggerFactory.getLogger(PlayerManager.class);
	
	@Autowired
	private EventDao eventDao;
	
	@Autowired
	private StatisticsManager statisticsManager;
	
	/**
	 * 确认一个赛事的出场情况，自动完成扣款
	 * 
	 * TODO: 写存储过程
	 * 
	 * 1. update isConfirmed in event
	 * 2. batch update player deposit
	 * 3. batch create player event activity
	 * @throws SQLException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void processEventConfirmation(Long eventId,
			List<FootballPlayerBalance> balances,
			List<FootballPlayerTeamActivity> activities) {
		Event event = eventDao.findById(Event.class, eventId);
		event.setIsConfirmed(Boolean.TRUE);
		eventDao.update(event);
		
		PreparedStatement updateDepositSqlStmt = null;
		PreparedStatement createBalanceLineSqlStmt = null;
		
		PreparedStatement updateAttendTeamPlayerStmt = null;
		PreparedStatement updateLateTeamPlayerStmt = null;
		PreparedStatement updateAbsenceTeamPlayerStmt = null;
		PreparedStatement createPlayerTeamActivityStmt = null;
		
		try {
			updateDepositSqlStmt = buildUpdateDepositStmt();
			createBalanceLineSqlStmt = buildCreateBalanceLineSqlStmt();
		
			for(FootballPlayerBalance balance : balances) {
				setUpdateDepositStatement(balance, updateDepositSqlStmt);
				setCreateBalanceLineSqlStmt(balance, createBalanceLineSqlStmt);
				
				updateDepositSqlStmt.addBatch();
				createBalanceLineSqlStmt.addBatch();
			}
			
			//split activities into 3 types 
			Collection<FootballPlayerTeamActivity> updateAttend = Collections2
					.filter(activities,
							new Predicate<FootballPlayerTeamActivity>() {
				@Override
				public boolean apply(FootballPlayerTeamActivity activity) {
					return PlayerTeamActivityType.ATTEND.equals(activity.getType());
				}
			});
			Collection<FootballPlayerTeamActivity> updateLate = Collections2
					.filter(activities,
							new Predicate<FootballPlayerTeamActivity>() {
				@Override
				public boolean apply(FootballPlayerTeamActivity activity) {
					return PlayerTeamActivityType.LATE.equals(activity.getType());
				}
			});
			Collection<FootballPlayerTeamActivity> updateAbsence = Collections2
					.filter(activities,
							new Predicate<FootballPlayerTeamActivity>() {
				@Override
				public boolean apply(FootballPlayerTeamActivity activity) {
					return PlayerTeamActivityType.ABSENCE.equals(activity.getType());
				}
			});
			
			createPlayerTeamActivityStmt = buildCreatePlayerTeamActivityStmt();
			
			for (FootballPlayerTeamActivity activity : updateAttend) {
				setCreatePlayerTeamActivityStmt(activity, createPlayerTeamActivityStmt);
				createPlayerTeamActivityStmt.addBatch();
			}
			
			for (FootballPlayerTeamActivity activity : updateLate) {
				setCreatePlayerTeamActivityStmt(activity, createPlayerTeamActivityStmt);
				createPlayerTeamActivityStmt.addBatch();
			}
			
			for (FootballPlayerTeamActivity activity : updateAbsence) {
				setCreatePlayerTeamActivityStmt(activity, createPlayerTeamActivityStmt);
				createPlayerTeamActivityStmt.addBatch();
			}
			
			try {
				updateDepositSqlStmt.executeBatch();
			} catch (Exception e) {			
				e.printStackTrace();
				throw new RuntimeException("updateDepositSqlStmt:", e);
			}
			
			try {
				createBalanceLineSqlStmt.executeBatch();
			} catch (Exception e) {			
				e.printStackTrace();
				throw new RuntimeException("createBalanceLineSqlStmt:", e);
			}
			
			try {
				int[] activityAccount = createPlayerTeamActivityStmt.executeBatch();
				checkUpdateCounts(activityAccount);
				// add by sam 统计出场信息
				buildPlayerRecord(event, updateAttend);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("createPlayerTeamActivityStmt:", e);
			}
		
		} catch (Exception ee) {
			ee.printStackTrace();
			throw new RuntimeException(ee);
		} finally {
			try {
				if (null != updateDepositSqlStmt) updateDepositSqlStmt.close();
				if (null != createBalanceLineSqlStmt) createBalanceLineSqlStmt.close();
				if (null != updateAttendTeamPlayerStmt) updateAttendTeamPlayerStmt.close();
				if (null != updateLateTeamPlayerStmt) updateLateTeamPlayerStmt.close();
				if (null != updateAbsenceTeamPlayerStmt) updateAbsenceTeamPlayerStmt.close();
				if (null != createPlayerTeamActivityStmt) createPlayerTeamActivityStmt.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	// add by sam 统计出场信息
	private void buildPlayerRecord(Event event,
			Collection<FootballPlayerTeamActivity> updateAttend) {
		try{
			statisticsManager.asyncBuildPlayerRecord(updateAttend, event);
		}catch(Exception e){
			logger.error("async build player record error",e);
		}
	}
	
	/**
	 * sql:
	 * 		insert into player_team_activity(player_id, team_id, event_id, type, create_by, create_dt, update_by, update_dt)
			select a.id, 1, 'ATTEND', a.`create_by`, a.`create_dt`, a.`update_by`, a.`update_dt` from team_player a 
			where a.player_id = 33 and a.team_id = 88 and status = 'ACTIVE'
	 * @return
	 * @throws SQLException 
	 */
	private PreparedStatement buildCreatePlayerTeamActivityStmt() {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into player_team_activity(player_id, team_id, event_id, type, create_by, update_by, create_dt, update_dt) ")
		   .append("select a.player_id, a.team_id, ?, ?, ?, ?, ?, ? from team_player a ")
		   .append("where a.player_id = ? and a.team_id = ? and status = 'ACTIVE'");
		
		PreparedStatement stmt = null;
		try {
			stmt = buildStatement(sql.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("buildCreatePlayerTeamActivityStmt", e);
		}
		return stmt;
	}
	
	public void setCreatePlayerTeamActivityStmt(
			FootballPlayerTeamActivity activity, PreparedStatement stmt) {
		Account account = AppContextHolder.getContext().getAccount();
		try {
			stmt.setLong(1, activity.getEventId());
			stmt.setString(2, activity.getType().toString());
			stmt.setString(3, account.getId().toString());
			stmt.setString(4, account.getId().toString());
			stmt.setTimestamp(5, new Timestamp(DateUtil.getCurrentDate().getTime()));
			stmt.setTimestamp(6, new Timestamp(DateUtil.getCurrentDate().getTime()));
			stmt.setLong(7, activity.getPlayerId());
			stmt.setLong(8, activity.getTeamId());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("setCreatePlayerTeamActivityStmt", e);
		}
	}
		
	public void setUpdateTeamPlayerStmt(
			FootballPlayerTeamActivity activity, PreparedStatement stmt) {
		try {
			stmt.setInt(1, activity.getDelta());
			stmt.setLong(2, activity.getPlayerId());
			stmt.setLong(3, activity.getTeamId());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("setUpdateTeamPlayerStmt", e);
		}
	}	

	/**
	 * Need select player_deposit_account id into player_balance_lines
	 * sql:
	 * 		insert into player_balance_lines(player_deposit_account_id, fee_type, pay_method, fee, create_by, create_dt, update_by, update_dt)
			select a.id, 'DEBIT', 'ZHIFUBAO', 99.00, a.`create_by`, a.`create_dt`, a.`update_by`, a.`update_dt` from player_deposit_account a where a.player_id = 33 and a.team_id = 88
	 * @return
	 * @throws SQLException
	 */
	private PreparedStatement buildCreateBalanceLineSqlStmt() {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into player_balance_lines(player_deposit_account_id, fee_type, pay_method, fee, eventId, comment, create_by, update_by, create_dt, update_dt) ")	
		   .append("select a.id, ?, ?, ?, ?, ?, ?, ?, ?, ? from player_deposit_account a where a.player_id = ? and a.team_id = ?");
		String createBalanceLineSql = sql.toString();
		PreparedStatement createBalanceLineSqlStmt;
		try {
			createBalanceLineSqlStmt = buildStatement(createBalanceLineSql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("buildCreateBalanceLineSqlStmt", e);
		}
		return createBalanceLineSqlStmt;
	}
	
	private void setCreateBalanceLineSqlStmt(FootballPlayerBalance balance,
			PreparedStatement stmt) {
		Account account = AppContextHolder.getContext().getAccount();
		try {
			stmt.setString(1, balance.getFeeType().toString());
			stmt.setString(2, balance.getPayMethod().toString());
			stmt.setBigDecimal(3, balance.getAmount());
			
			stmt.setLong(4, balance.getEventId());
			stmt.setString(5, balance.getComment());
			
			stmt.setString(6, account.getId().toString());
			stmt.setString(7, account.getId().toString());
			stmt.setTimestamp(8, new Timestamp(DateUtil.getCurrentDate().getTime()));
			stmt.setTimestamp(9, new Timestamp(DateUtil.getCurrentDate().getTime()));
			stmt.setLong(10, balance.getPlayerId());
			stmt.setLong(11, balance.getTeamId());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("setCreateBalanceLineSqlStmt", e);
		}
		
	}
		
	/**
	 * Build player deposit statement for creation
	 * sql: 
	  		update player_deposit_account set balances = balances + ? where player_id = ? and team_id = ?
	 * @param updateDepositSqlStmt
	 * @param balance
	 * @throws SQLException 
	 */
	private PreparedStatement buildUpdateDepositStmt() {
		String updateDepositSql = "update player_deposit_account set balances = balances + ? where player_id = ? and team_id = ?";
		PreparedStatement updateDepositSqlStmt;
		try {
			updateDepositSqlStmt = buildStatement(updateDepositSql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("setCreateBalanceLineSqlStmt", e);
		}
		return updateDepositSqlStmt;
	}

	private void setUpdateDepositStatement(FootballPlayerBalance balance,
			PreparedStatement updateDepositSqlStmt) {
		BigDecimal amount = balance.getAmount();
		if (FeeType.DEBIT.equals(balance.getFeeType())) {
            amount = amount.abs();
        }
        
        if (FeeType.CREDIT.equals(balance.getFeeType())) {
            amount = BigDecimal.ZERO.subtract(amount);
        }
        
		try {
			updateDepositSqlStmt.setBigDecimal(1, amount);
			updateDepositSqlStmt.setLong(2, balance.getPlayerId());
			updateDepositSqlStmt.setLong(3, balance.getTeamId());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("setUpdateDepositStatement", e);
		}
	}

	private PreparedStatement buildStatement(String sql)
			throws SQLException {
		return SessionFactoryUtils.getDataSource(eventDao.getSessionFactory())
				.getConnection().prepareStatement(sql);
	}
	
	private static void checkUpdateCounts(int[] updateCounts) {
	    for (int i = 0; i < updateCounts.length; i++) {
	      if (updateCounts[i] >= 0) {
	        System.out.println("Successfully executed; updateCount=" + updateCounts[i]);
	      } else if (updateCounts[i] == Statement.SUCCESS_NO_INFO) {
	        System.out.println("Successfully executed; updateCount=Statement.SUCCESS_NO_INFO");
	      } else if (updateCounts[i] == Statement.EXECUTE_FAILED) {
	        System.out.println("Failed to execute; updateCount=Statement.EXECUTE_FAILED");
	      }
	    }
	  }

}
