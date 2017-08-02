package com.footballer.rest.api.v1.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.internal.TypeLocatorImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.EnumType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.type.TypeResolver;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.footballer.filter.AppContextHolder;
import com.footballer.rest.api.v1.domain.PlayerTeamSummary;
import com.footballer.rest.api.v1.vo.Account;
import com.footballer.rest.api.v1.vo.Player;
import com.footballer.rest.api.v1.vo.PlayerTeamActivity;
import com.footballer.rest.api.v1.vo.Team;
import com.footballer.rest.api.v1.vo.TeamPlayer;
import com.footballer.rest.api.v1.vo.TeamPlayer.TEAM_PLAYER_STATUS;
import com.footballer.rest.api.v1.vo.TeamPlayerInvitation;
import com.footballer.rest.api.v1.vo.enumType.InvitationResponseType;
import com.footballer.rest.api.v1.vo.enumType.InvitationType;
import com.footballer.rest.api.v1.vo.enumType.PlayerPosition;
import com.footballer.rest.api.v1.vo.enumType.PlayerTeamActivityType;
import com.footballer.util.CommonUtil;
import com.footballer.util.DateUtil;
import com.footballer.util.ObjectUtil;
import com.footballer.util.StringUtil;

/**
 * Created by justin on 6/29/14.
 */

public class TeamDao extends BaseDao {

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public Team createNewTeam(Team team)  {
    	CommonUtil.addAuditableAttributes(team);
        return createTeamByPlayer(team);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public Team createNewTeam(Team team,Long playerId)  {
    	CommonUtil.addAuditableAttributes(team);
        return createTeamByPlayer(team,playerId);
    }
    
    public void addPlayersToTeam(Long teamId, Set<Long> playerIds) {
    	if (!CollectionUtils.isEmpty(playerIds)) {
    		Account account = AppContextHolder.getContext().getAccount();
    		String sql = "INSERT INTO team_player(team_id, player_id, status, create_by, update_by, create_dt, update_dt) VALUES (?,?,?,?,?,?,?)";
        	PreparedStatement stmt;
			try {
				stmt = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection().prepareStatement(sql);
				for (Long playerId : playerIds) {
	        		stmt.setLong(1, teamId);
	            	stmt.setLong(2, playerId);
	            	stmt.setString(3, TeamPlayer.TEAM_PLAYER_STATUS.ACTIVE.toString());
	            	stmt.setString(4, account.getId().toString());
	            	stmt.setString(5, account.getId().toString());
	            	stmt.setTimestamp(6, new Timestamp(DateUtil.getCurrentDate().getTime()));
	            	stmt.setTimestamp(7, new Timestamp(DateUtil.getCurrentDate().getTime()));
	            	stmt.addBatch();
	        	}
	        	
	        	stmt.executeBatch();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
    	}
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void addPlayerToTeam(Long teamId) {
    	Account account = AppContextHolder.getContext().getAccount();
    	TeamPlayer teamPlayer = new TeamPlayer();
    	teamPlayer.init();
    	teamPlayer.setTeamId(teamId);
    	teamPlayer.setPlayerId(account.getId());
    	teamPlayer.setStatus(TeamPlayer.TEAM_PLAYER_STATUS.ACTIVE.toString());
    	
    	save(teamPlayer);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public TeamPlayer addPlayerToTeam(Long teamId, Long playerId) {
    	Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(TeamPlayer.class);
        criteria.add(Restrictions.eq("teamId", teamId))
        		    .add(Restrictions.eq("playerId", playerId));
        TeamPlayer tp = (TeamPlayer) criteria.uniqueResult();
        if(ObjectUtil.isNull(tp)){
        	TeamPlayer teamPlayer = new TeamPlayer();
        	teamPlayer.init();
        	teamPlayer.setTeamId(teamId);
        	teamPlayer.setPlayerId(playerId);
        	teamPlayer.setStatus(TeamPlayer.TEAM_PLAYER_STATUS.ACTIVE.toString());
        	save(teamPlayer);
        }else if(!TEAM_PLAYER_STATUS.ACTIVE.name().equals(tp.getStatus())){
        	tp.setStatus(TeamPlayer.TEAM_PLAYER_STATUS.ACTIVE.name());
        	this.update(tp);
        }
        return tp;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public Team createTeamByPlayer(Team team) {
    	CommonUtil.addAuditableAttributes(team);
    	Long teamId = (Long) save(team);
    	addPlayerToTeam(teamId);    	
    	
    	return team;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public Team createTeamByPlayer(Team team,Long playerId) {
    	CommonUtil.addAuditableAttributes(team);
    	Long teamId = (Long) save(team);
    	addPlayerToTeam(teamId,playerId);    	
    	team.setId(teamId);
    	return team;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void update(Team team)  {    	
    	team.setUpdateDt(DateUtil.getCurrentDate());
        saveOrUpdate(team);
        flush();
    }

    @SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<Team> findTeamsByName(String name) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(
        		Team.class);
        // TODO: add city criteria
        if (null != name) {
        	criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        criteria.addOrder(Order.asc("name"));
        
        List<Team> teams = criteria.list();
                
        return teams;
    }
    
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<TeamBasic> findTeamsByCaptainUserId(Long id) {
    	Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Team.class);
    	List<Team> teams = criteria.add(Restrictions.eq("captainUserId", id)).list();
    	List<TeamBasic> results = new ArrayList<TeamDao.TeamBasic>();
    	if (!CollectionUtils.isEmpty(teams)) {
    		for (Team team : teams) {
    			TeamBasic teamBasic = new TeamBasic();
    			
    			teamBasic.setId(team.getId());
    			teamBasic.setTeamId(team.getId());
    			teamBasic.setName(team.getName());
    			teamBasic.setLogo(team.getLogo());
    			teamBasic.setNumber(team.getNumber());
    			
    			results.add(teamBasic);
    		}
    	}
    	
    	return results;
    }
    
    public static class TeamBasic {
    	private Long id;
    	private String name;
    	private String logo;
    	private Integer number;
    	private Long teamId;
    	
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getLogo() {
			return logo;
		}
		public void setLogo(String logo) {
			this.logo = logo;
		}
		public Integer getNumber() {
			return number;
		}
		public void setNumber(Integer number) {
			this.number = number;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Long getTeamId() {
			return teamId;
		}
		public void setTeamId(Long teamId) {
			this.teamId = teamId;
		}
    }

//    public Team findTeamById(Long id) {        
//        Property idProperty = Property.forName("id");
//        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(
//        		Team.class);
//        criteria.add(idProperty.eq(id));
//        return (Team) criteria.uniqueResult();
//    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public Team findTeamPlayers(Long id) {
        Session session = getSessionFactory().getCurrentSession();
        Team team =  (Team) session.createCriteria(Team.class)
                .setFetchMode("players", FetchMode.JOIN)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        
        //好恶心的实现，后面再迁移到V2 add by  sam
        filterNoActive(team);
        return team;
    }

	private void filterNoActive(Team team) {
		if(team == null){
        	return ;
        }
        Iterator<Player> it = team.getPlayers().iterator();
        TeamPlayer tp = null;
        Player p = null;
        while(it.hasNext()){
        	p = it.next();
        	tp = this.findActiveTeamPlayer(team.getId(), p.getId());
        	if(tp == null){
        		it.remove();
        	}
        }
	}
    
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public List<Player> findApplicantPlayers(Long teamId){
		Criteria p_criteria = getSessionFactory().getCurrentSession().createCriteria(Player.class,"p");
		DetachedCriteria tpi_criteria = DetachedCriteria.forClass(TeamPlayerInvitation.class, "tpi");
		tpi_criteria.add(Property.forName("p.id").eqProperty("tpi.playerId"));
		tpi_criteria.add(Restrictions.eq("teamId", teamId));
		tpi_criteria.add(Restrictions.eq("type", InvitationType.PLAYERTOTEAM));
		tpi_criteria.add(Restrictions.eq("status", InvitationResponseType.APPLY));
		p_criteria.add(Subqueries.exists(tpi_criteria.setProjection(Projections.property("tpi.playerId"))));
		p_criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Player> applicantPlayers= p_criteria.list();
		
		return applicantPlayers;
    }
    
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public List<Player> findInvitedPlayers(Long teamId){
		Criteria p_criteria = getSessionFactory().getCurrentSession().createCriteria(Player.class,"p");
		DetachedCriteria tpi_criteria = DetachedCriteria.forClass(TeamPlayerInvitation.class, "tpi");
		tpi_criteria.add(Property.forName("p.id").eqProperty("tpi.playerId"));
		tpi_criteria.add(Restrictions.eq("teamId", teamId));
		tpi_criteria.add(Restrictions.eq("type", InvitationType.TEAMTOPLAYER));
		tpi_criteria.add(Restrictions.eq("status", InvitationResponseType.APPLY));
		p_criteria.add(Subqueries.exists(tpi_criteria.setProjection(Projections.property("tpi.playerId"))));
		p_criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Player> InvitedPlayers= p_criteria.list();
		
		return InvitedPlayers;
    }
    
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public List<Team> findTeamsAsCaptainByPlayerId(Long playerId){
    	Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Team.class);
    	criteria.add(Restrictions.or(
    			Restrictions.eq("captainUserId", playerId),
    			Restrictions.eq("viceCaptainUserId", playerId)));
    	criteria.addOrder(Order.asc("name"));
        return criteria.list();
    }
    
    
    @SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<Team> findTeamsByInfo(String name, String technical, Number is_recruit, Number is_strong) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Team.class);
        
        if(ObjectUtil.isNotNull(name)){
        	criteria.add(Restrictions.ilike("name", "%" + name + "%"));
        }
        if(ObjectUtil.isNotNull(name)){
        	criteria.add(Property.forName("technical").eq(technical));
        }
        if(ObjectUtil.isNotNull(name)){
        	criteria.add(Property.forName("is_recruit").eq(is_recruit));
        }
        if(ObjectUtil.isNotNull(name)){
        	criteria.add(Property.forName("is_strong").eq(is_strong));
        }
        criteria.addOrder(Order.asc("name"));
        return criteria.list();

    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public Boolean isPlayerInTeam(Long teamId, Long playerId){
    	Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(TeamPlayer.class);
    	criteria.add(Property.forName("teamId").eq(teamId));
    	criteria.add(Property.forName("playerId").eq(playerId));
    	criteria.add(Property.forName("status").eq(TEAM_PLAYER_STATUS.ACTIVE.name()));
    	if(criteria.list().size()>0){
    		return true;
    	}
		return false;
    }
    
    @SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public List<PlayerTeamSummary> findPlayerTeamSummary(Long teamId) {
    	Session session = getSessionFactory().getCurrentSession();
    	
    	Properties params = new Properties();
    	params.put("enumClass", "com.footballer.rest.api.v1.vo.enumType.PlayerPosition");
    	params.put("type", "12");
    	
    	Type userEnumType = new TypeLocatorImpl(new TypeResolver()).custom(EnumType.class, params); 
    	
    	Query query = session.createSQLQuery("select t.id  as teamId, " + 
						       "t.name           as teamName, "   +
						       "t.logo           as teamLogo, " +
						       "t.captain_user_id as teamCaptainId, " +
						       "fp.id            as playerId, "   +
						       "fp.nick_name     as playerName, " +
						       "pda.`balances`   as balances, "   +
						       "tp.player_number as playerNumber, " +
						       "tp.position      as position, "    +
						       "tp.attend_total  as attendTotal, " +
						       "tp.late_total    as lateTotal, "   +
						       "tp.absence_total as absenceTotal, " +
						       "tp.name          as playerTeamName, " +
						       "fp.imageUrl      as imageUrl " +
						"from team t " +
						"inner join team_player tp on t.id = tp.team_id " +
						"inner join football_player fp on tp.player_id = fp.id " +
						"left join player_deposit_account pda on  pda.player_id = fp.id and pda.team_id = t.id " + 
						"where t.id = :teamId " +
						"order by t.id, fp.id")
						.addScalar("teamId", LongType.INSTANCE)
						.addScalar("playerId", LongType.INSTANCE)
						.addScalar("teamName", StringType.INSTANCE)
						.addScalar("teamLogo", StringType.INSTANCE)
						.addScalar("teamCaptainId", LongType.INSTANCE)
						.addScalar("playerName", StringType.INSTANCE)
						.addScalar("balances", BigDecimalType.INSTANCE)
						.addScalar("playerNumber", IntegerType.INSTANCE)
						.addScalar("position", userEnumType)
						.addScalar("attendTotal", IntegerType.INSTANCE)
						.addScalar("lateTotal", IntegerType.INSTANCE)
						.addScalar("absenceTotal", IntegerType.INSTANCE)
						.addScalar("playerTeamName", StringType.INSTANCE)
						.addScalar("imageUrl", StringType.INSTANCE)
						.setResultTransformer(Transformers.aliasToBean(PlayerTeamSummary.class))
						.setParameter("teamId", teamId)
						;
    	return query.list();
    }
    
    @Transactional
    public TeamPlayer updatePlayerTeamSummary(TeamPlayer teamPlayer) {
		TeamPlayer existTeamPlayer = findActiveTeamPlayer(
				teamPlayer.getTeamId(), teamPlayer.getPlayerId());
    	
    	teamPlayer.setUpdateDt(DateUtil.getCurrentDate());
    	
    	Integer playerNumber = teamPlayer.getPlayerNumber();
    	PlayerPosition position = teamPlayer.getPosition();
    	Integer attendTotal = teamPlayer.getAttendTotal();
    	Integer lateTotal = teamPlayer.getLateTotal();
    	Integer absenceTotal = teamPlayer.getAbsenceTotal();
    	String name = teamPlayer.getName();
    	
    	if (null != playerNumber) {existTeamPlayer.setPlayerNumber(playerNumber);}
    	if (null != position) {existTeamPlayer.setPosition(position);}
    	if (null != attendTotal) {existTeamPlayer.setAttendTotal(attendTotal);}
    	if (null != lateTotal) {existTeamPlayer.setLateTotal(lateTotal);}
    	if (null != absenceTotal) {existTeamPlayer.setAbsenceTotal(absenceTotal);}
    	if (null != name) {existTeamPlayer.setName(name);}
    	
    	super.update(existTeamPlayer);
    	
    	return existTeamPlayer;
    }
    
	public TeamPlayer updatePlayerTeamSummaryByType(Long playerId, Long teamId,
			Integer delta, PlayerTeamActivityType type) {
		
		TeamPlayer teamPlayer = findActiveTeamPlayer(teamId, playerId);    	
    	
    	if (null != teamPlayer) {
    		if (PlayerTeamActivityType.ATTEND.equals(type)) {
    			teamPlayer.setAttendTotal(teamPlayer.getAttendTotal() + delta);
    		} else if (PlayerTeamActivityType.LATE.equals(type)) {
    			teamPlayer.setLateTotal(teamPlayer.getLateTotal() + delta);
    		} else if (PlayerTeamActivityType.ABSENCE.equals(type)) {
    			teamPlayer.setAbsenceTotal(teamPlayer.getAbsenceTotal() + delta);
    		} else {
    			throw new RuntimeException("Type can't match: ATTEND, LATE, ABSENCE");
    		}
    		
    		teamPlayer.initUpdate();
        	update(teamPlayer);
    	}
    	
    	return teamPlayer;
    }
    
    public TeamPlayer findActiveTeamPlayer(Long teamId, Long playerId) {
    	Session session = getSessionFactory().getCurrentSession();
    	TeamPlayer existTeamPlayer = (TeamPlayer) session.createCriteria(TeamPlayer.class)
			       .add(Restrictions.eq("teamId", teamId))
			       .add(Restrictions.eq("playerId", playerId))
			       .add(Restrictions.eq("status", "ACTIVE"))
			       .uniqueResult();
    	return existTeamPlayer;
    }
    
    public TeamPlayer findTeamPlayer(Long teamId, Long playerId) {
    	Session session = getSessionFactory().getCurrentSession();
    	TeamPlayer existTeamPlayer = (TeamPlayer) session.createCriteria(TeamPlayer.class)
			       .add(Restrictions.eq("teamId", teamId))
			       .add(Restrictions.eq("playerId", playerId))
			       .uniqueResult();
    	return existTeamPlayer;
    }
    	
	@SuppressWarnings("unchecked")
	public List<PlayerTeamActivity> findPlayerTeamActivities(Long playerId, Long teamId) {
		
		if (null == teamId) return new ArrayList<PlayerTeamActivity>();
		
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(PlayerTeamActivity.class);
		
		criteria.add(Restrictions.eq("playerId", playerId));
		criteria.add(Restrictions.eq("teamId", teamId));
			
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PlayerTeamActivity> findPlayerEventActivities(Long eventId) {
		
		if (null == eventId) return new ArrayList<PlayerTeamActivity>();
		
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(PlayerTeamActivity.class);
		
		criteria.add(Restrictions.eq("eventId", eventId));			
		return criteria.list();
	}

	 @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void createPlayerTeamActivity(Long playerId, Long teamId,
			Long eventId, PlayerTeamActivityType type) {
		 
		 Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PlayerTeamActivity.class);
		 criteria.add(Restrictions.eq("playerId", playerId))
		 			 .add(Restrictions.eq("eventId", eventId));
		 if(ObjectUtil.isNotNull(teamId)){
			 criteria.add(Restrictions.eq("teamId", teamId));
		 }
		 
		 PlayerTeamActivity pta =  (PlayerTeamActivity) criteria.uniqueResult();
		 
		 if(ObjectUtil.isNotNull(pta)){
			 pta.setType(type);
			 pta.setUpdateDt(DateUtil.getCurrentDate());
			 update(pta);
		 }else{
			 pta = new PlayerTeamActivity();
			 pta.setPlayerId(playerId);
			 pta.setTeamId(teamId);
			 pta.setEventId(eventId);
			 pta.setType(type);
			 pta.init();
			 save(pta);
		 }
	}
	 @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	 public void updateTeamPlayerStatus(Long teamId,Long playerId,TEAM_PLAYER_STATUS status){
		 if(status == null){
			 return ;
		 }
		 TeamPlayer tp = null;
		 tp = this.findTeamPlayer(teamId, playerId);	 
		 if(tp != null){
			 tp.setStatus(status.name());
			 this.update(tp);
		 }
	 }

	 @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void updateTeamInfo(Team team) {
		if(team == null){
			return;
		}
			
		Team dbTeam = this.get(Team.class, team.getId());
		if(team.getNumber() != null){
			dbTeam.setNumber(team.getNumber());
		}
		if(!StringUtil.isEmpty(team.getLogo())){
			dbTeam.setLogo(team.getLogo());
		}
		if(!StringUtil.isEmpty(team.getTechnical())){
			dbTeam.setTechnical(team.getTechnical());
		}
		if(!StringUtil.isEmpty(team.getName())){
			dbTeam.setName(team.getName());
		}
		if(team.getCaptainUserId() != null){
			dbTeam.setCaptainUserId(team.getCaptainUserId());
		}
		if(team.getViceCaptainUserId() != null){
			dbTeam.setViceCaptainUserId(team.getViceCaptainUserId());
		}
		this.update(dbTeam);
	}
	 
	 @SuppressWarnings("unchecked")
	 @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<Team> findTeamByActivePlayer(Long playerId){
		 String hql = "select t.id,t.name,t.logo, t.captain_user_id as captainUserId ," +
		 		" t.viceCaptain_user_id as viceCaptainUserId ,t.number,t.technical " +
		 		" from team t , team_player tp " +
		 		" where  t.id = tp.team_id and  tp.status = :status and tp.player_id = :playerId ";
		 Query query = getSessionFactory().getCurrentSession().createSQLQuery(hql)
				 .addScalar("id", LongType.INSTANCE)
				 .addScalar("logo", StringType.INSTANCE)
				 .addScalar("name", StringType.INSTANCE)
				 .addScalar("captainUserId", LongType.INSTANCE)
				 .addScalar("viceCaptainUserId", LongType.INSTANCE)
				 .addScalar("number", IntegerType.INSTANCE)
				 .addScalar("technical", StringType.INSTANCE)
				 .setResultTransformer(Transformers.aliasToBean(Team.class))
				 .setParameter("status", TEAM_PLAYER_STATUS.ACTIVE.name())
				 .setParameter("playerId", playerId);
		 return query.list();
	 }
}
