package com.footballer.rest.api.v1.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.TypeLocatorImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.EnumType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.type.TypeResolver;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.filter.AppContextHolder;
import com.footballer.rest.api.v1.domain.PlayerTeamSummary;
import com.footballer.rest.api.v1.domain.UserWithStatus;
import com.footballer.rest.api.v1.vo.Account;
import com.footballer.rest.api.v1.vo.Address;
import com.footballer.rest.api.v1.vo.Player;
import com.footballer.rest.api.v1.vo.PlayerAppToken;
import com.footballer.rest.api.v1.vo.PlayerBalanceLine;
import com.footballer.rest.api.v1.vo.PlayerDepositAccount;
import com.footballer.rest.api.v1.vo.enumType.AppTokenType;
import com.footballer.rest.api.v1.vo.enumType.FeeType;
import com.footballer.rest.api.v1.vo.enumType.PayMethod;
import com.footballer.util.ObjectUtil;

/**
 * Created by ian on 6/14/14.
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
public class UserDao extends BaseDao {

    private final String QUERY_USERS_BY_NAME_PASSWORD = "from Account u where u.name = ? and u.password = ?";
    private final String QUERY_USERS_BY_NAME = "from Account u where u.name = ?";

    private final String QUERY_USERS_BY_IDENTIFY = "from Account u join fetch u.userToken t where t.identity = ?";

    public Long save(Account account) {
        Long id = (Long) super.save(account);
        flush();
        return id;
    }

    public Player findPlayer(Long id) {
        Session session = getSessionFactory().getCurrentSession();
        Player player = (Player) session.get(Player.class, id);
        if (null != player) {
            player.setTeams(player.getTeams());
        }
        return player;
    }
    
    @SuppressWarnings("unchecked")
	public List<Account> findUsersByName(String name) {
        return (List<Account>) find(QUERY_USERS_BY_NAME, name);
    }

    @SuppressWarnings("unchecked")
	public List<Account> findUsersByNameAndPassword(String name, String password) {
        return (List<Account>) find(QUERY_USERS_BY_NAME_PASSWORD, name, password);
    }

    @SuppressWarnings("unchecked")
	public List<Account> findUsersByIdentify(String identify) {
        return (List<Account>) find(QUERY_USERS_BY_IDENTIFY, identify);
    }
    
    public void createUserPlayer(Player player) {
        Account account = player.getAccount();
        if (null != findPlayer(account.getId())) {
            throw new RuntimeException("Account:" + account.getName() + " has already create player!");
        }
        save(player);
    }

    
    public void updateUserPlayer(Player player) {
    	Account account = AppContextHolder.getContext().getAccount();
        Player foundPlayer = findById(Player.class, account.getId());
        
        if (null != player.getImageUrl()) foundPlayer.setImageUrl(player.getImageUrl());
        if (null != player.getNickName()) foundPlayer.setNickName(player.getNickName());
        if (null != player.getAddress()) {
        	Address foundAddress = foundPlayer.getAddress();
        	Address playerAddress = player.getAddress();
        	if (null != foundAddress) {
        		if (null != playerAddress.getLatitude()) foundAddress.setLatitude(player.getAddress().getLatitude());
            	if (null != playerAddress.getLongitude()) foundAddress.setLongitude(player.getAddress().getLongitude());
            	if (null != playerAddress.getName()) foundAddress.setName(player.getAddress().getName());
        	} else {
        		//create account with a empty player update 
        		super.save(playerAddress);
        		foundPlayer.setAddress(playerAddress);
        	}
        }
        if (null != player.getDeviceToken()) foundPlayer.setDeviceToken(player.getDeviceToken());
        update(foundPlayer);
    }
    
    public void updateAccountPhone(String phone, Long accountId) {
		Account savedAccount = findById(Account.class, accountId);
		savedAccount.setCellphone(phone);
		update(savedAccount);
	}
    
    public void updatePlayerDeviceToken(String deviceToken) {
    	Account account = AppContextHolder.getContext().getAccount();
    	AppTokenType appTokenType = AppContextHolder.getContext().getAppTokenType();
        
    	// update device token to a player
        Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(PlayerAppToken.class);
        criteria.add(Restrictions.eq("token", deviceToken))
        		.add(Restrictions.eq("type", appTokenType));
        
        PlayerAppToken playerAppToken = (PlayerAppToken) criteria.uniqueResult();
        
        if (null != playerAppToken) {
        	if (playerAppToken.getPlayerId() != account.getId()) {
        		playerAppToken.setPlayerId(account.getId());
            	playerAppToken.initUpdate();
            	super.update(playerAppToken);
        	}
        } else {
            // if there is an existing player, then update device token
            criteria = getSessionFactory().getCurrentSession()
    				.createCriteria(PlayerAppToken.class);
            criteria.add(Restrictions.eq("playerId", account.getId()))
            		.add(Restrictions.eq("type", appTokenType));
            
            playerAppToken = (PlayerAppToken) criteria.uniqueResult();
            
            if (null != playerAppToken) {
            	if (!playerAppToken.getToken().equals(deviceToken)) {
            		playerAppToken.setToken(deviceToken);
            		playerAppToken.initUpdate();
                	super.update(playerAppToken);
            	}
            } else {
                // otherwise create an new one
        		playerAppToken = new PlayerAppToken();
        		playerAppToken.setToken(deviceToken);
        		playerAppToken.setPlayerId(account.getId());
        		playerAppToken.setType(appTokenType);
        		playerAppToken.init();
        		super.save(playerAppToken);
            }
        } 
    } 
    
    @SuppressWarnings("unchecked")
	public List<Account> findAllAccounts() {
    	 Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Account.class);
    	 criteria.addOrder(Order.asc("id"));
         return criteria.list();
    }
        
	public PlayerDepositAccount updatePlayerBalances(Long playerId,
			Long teamId, BigDecimal amount) {
    	
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(PlayerDepositAccount.class);
		criteria.add(Restrictions.eq("playerId", playerId))
		        .add(Restrictions.eq("teamId", teamId));
		
		PlayerDepositAccount account = (PlayerDepositAccount) criteria.uniqueResult();
		
		if (null != account) {
			account.setBalances(account.getBalances().add(amount));
			account.initUpdate();
			
			update(account);
		}
		
		return account;
    }
	
	/**
	 * 创建球员在球队的资金账户
	 * @param playerId
	 * @param teamId
	 */
	public Long createPlayerDepositAccount(Long playerId, Long teamId) {
		PlayerDepositAccount account = new PlayerDepositAccount();
		account.setPlayerId(playerId);
		account.setTeamId(teamId);
		account.setBalances(BigDecimal.ZERO);
		account.init();
		return (Long) super.save(account);
	}
    
	public void createPlayerBalancesLine(PlayerDepositAccount account,
			FeeType feeType, PayMethod payMethod, BigDecimal amount, String comment, Long eventId) {
		PlayerBalanceLine lines = new PlayerBalanceLine();
		
		lines.setAccount(account);
		lines.setFeeType(feeType);
		lines.setPayMethod(payMethod);
		lines.setFee(amount);
		lines.setComment(comment);
		lines.setEventId(eventId);
		
		lines.init();
		
		super.save(lines);
    }	
	
	public List<PlayerBalanceLine> findPlayerBalanceLines(Long playerId, Long teamId) {
		List<PlayerBalanceLine> lines = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		PlayerDepositAccount account = (PlayerDepositAccount) session.createCriteria(PlayerDepositAccount.class)
				       .setFetchMode("playerBalanceLines", FetchMode.JOIN)
				       .add(Restrictions.eq("playerId", playerId))
				       .add(Restrictions.eq("teamId", teamId))
				       .uniqueResult();
		return account == null ? lines  : account.getPlayerBalanceLines();	   
	}
	
	/**
	 * 获取一场比赛所有人的缴费/欠费记录
	 * @param playerId
	 * @param teamId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PlayerBalanceLine> findPlayerBalanceLines(Long eventId) {
		Session session = getSessionFactory().getCurrentSession();
		
		Properties payMthod = new Properties();
    	payMthod.put("enumClass", "com.footballer.rest.api.v1.vo.enumType.PayMethod");
    	payMthod.put("type", "12");
    	
    	Type payMethodEnumType = new TypeLocatorImpl(new TypeResolver()).custom(EnumType.class, payMthod);
    	
    	Properties params = new Properties();
    	params.put("enumClass", "com.footballer.rest.api.v1.vo.enumType.FeeType");
    	params.put("type", "12");
    	
    	Type feeTypeEnumType = new TypeLocatorImpl(new TypeResolver()).custom(EnumType.class, params);
    	
		String sql = "select pbl.fee        as fee, " + 
    	                    "pbl.eventId    as eventId, " +
    	                    "pbl.fee_type   as feeType, " +
					        "pbl.pay_method as payMethod, " +
					        "pbl.comment    as comment, " +
					        "fp.`nick_name` as playerName, " +  
					        "pda.`balances` as balances " +  
					"from player_balance_lines pbl " +
					"inner join player_deposit_account pda on pbl.player_deposit_account_id = pda.id " +
					"inner join football_player fp on pda.player_id = fp.id " +
					"where pbl.eventId = :eventId";
		
		Query query = session.createSQLQuery(sql)
				.addScalar("fee", BigDecimalType.INSTANCE)
				.addScalar("eventId", LongType.INSTANCE)
				.addScalar("feeType", feeTypeEnumType)
				.addScalar("payMethod", payMethodEnumType)
				.addScalar("comment", StringType.INSTANCE)
				.addScalar("playerName", StringType.INSTANCE)
				.addScalar("balances", BigDecimalType.INSTANCE)
				.setResultTransformer(Transformers.aliasToBean(PlayerBalanceLine.class))
				.setParameter("eventId", eventId);
		
		return query.list();
	}
	
	/**
	 * 获取指定球员的所有球队的球员列表
	 * @param playerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PlayerTeamSummary> findPlayerTeamSummary(Long playerId) {
		Session session = getSessionFactory().getCurrentSession();
		String sql = "select tp.team_id     as teamId, "    + 
					         "t.name        as teamName, " +
					         "t.logo        as teamLogo, " +
					         "t.captain_user_id as teamCaptainId, "+
					         "tp.player_id  as playerId, " + 
					         "fp.nick_name  as playerName, " +
					         "fp.imageUrl   as imageUrl " +
                     "from team_player tp "                 + 
                     "inner join team t on tp.team_id = t.id " +
                     "inner join football_player fp on tp.player_id = fp.id " +
                     "where tp.team_id in ( " +
										"select team_id from team_player " +
										"where player_id = :playerId " +
										  "and status = 'ACTIVE' " +
										 ") " +
					"order by team_id, player_id";
    	Query query = session.createSQLQuery(sql)
						.addScalar("teamId", LongType.INSTANCE)
						.addScalar("playerId", LongType.INSTANCE)
						.addScalar("teamCaptainId", LongType.INSTANCE)
						.addScalar("teamName", StringType.INSTANCE)
						.addScalar("teamLogo", StringType.INSTANCE)
						.addScalar("playerName", StringType.INSTANCE)
						.addScalar("imageUrl", StringType.INSTANCE)
						.setResultTransformer(Transformers.aliasToBean(PlayerTeamSummary.class))
						.setParameter("playerId", playerId)
						;
    	return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<UserWithStatus> findCurrentUsersAddressListFriends(Long playerId, List<String> cellphoneList) {
		Session session = getSessionFactory().getCurrentSession();

		String sql = "select fp.id  as id, "    + 
		         	 "fp.nick_name  as name, " +
		             "a.name        as cellphoneNO, " +
		             "fp.imageUrl   as imageUrl "+
		             "from football_player fp "                 + 
		             "inner join account a on a.id = fp.id " +
		             "where a.name in (:cellphoneList) " +
		             "and fp.id not in (SELECT player_id as ids FROM player_player_invitation " +
		 				   			"where from_player_id= :playerId " +
		 				   			"union SELECT from_player_id as ids FROM player_player_invitation " +
		 				   			"where player_id= :playerId)";

		System.out.println(sql);

		Query query = session.createSQLQuery(sql)
							.addScalar("id", LongType.INSTANCE)
							.addScalar("name", StringType.INSTANCE)
							.addScalar("cellphoneNO", StringType.INSTANCE)
							.addScalar("imageUrl", StringType.INSTANCE)
							.setResultTransformer(Transformers.aliasToBean(UserWithStatus.class))
							.setParameter("playerId", playerId)
							.setParameterList("cellphoneList", cellphoneList);
		
		return query.list();
	}
	
	public UserWithStatus findUserAndRelationshipByCellPhone(String criteria) {
		
		Account currentAccount = AppContextHolder.getContext().getAccount();
		Long currentUserId = currentAccount.getId();
		
		
		String sql ="select fp.id as id, fp.nick_name  as name, a.name as cellphoneNO, fp.imageUrl as imageUrl, ppi.from_player_id as fromPlayerID, ppi.player_id as playerId, ppi.status as status "+
				    "from football_player fp "+ 
				    "inner join account a on a.id = fp.id " + 
				    "left join player_player_invitation ppi on " + 
				    "ppi.from_player_id = fp.id and ppi.player_id = :currentUserId or ppi.player_id = fp.id and ppi.from_player_id = :currentUserId "+
				    "where a.name = :criteria";

		System.out.println(sql);

		Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
							.addScalar("id", LongType.INSTANCE)
							.addScalar("name", StringType.INSTANCE)
							.addScalar("cellphoneNO", StringType.INSTANCE)
							.addScalar("imageUrl", StringType.INSTANCE)
							.addScalar("fromPlayerId", LongType.INSTANCE)
							.addScalar("playerId", LongType.INSTANCE)
							.addScalar("status", StringType.INSTANCE)
							.setResultTransformer(Transformers.aliasToBean(UserWithStatus.class))
							.setParameter("currentUserId", currentUserId)
							.setParameter("criteria", criteria);
		
		UserWithStatus u = (UserWithStatus) query.uniqueResult(); 
		return setUserStatus(currentUserId, u);
	}
	
	public List<UserWithStatus> findUserAndRelationshipByNickName(String criteria) {
		
		Account currentAccount = AppContextHolder.getContext().getAccount();
		Long currentUserId = currentAccount.getId();
		
		
		String sql ="select fp.id as id, fp.nick_name  as name, a.name as cellphoneNO, fp.imageUrl as imageUrl, ppi.from_player_id as fromPlayerID, ppi.player_id as playerId, ppi.status as status "+
				    "from football_player fp "+ 
				    "inner join account a on a.id = fp.id " + 
				    "left join player_player_invitation ppi on " + 
				    "ppi.from_player_id = fp.id and ppi.player_id = :currentUserId or ppi.player_id = fp.id and ppi.from_player_id = :currentUserId "+
				    "where fp.nick_name like :criteria";

		System.out.println(sql);

		Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
							.addScalar("id", LongType.INSTANCE)
							.addScalar("name", StringType.INSTANCE)
							.addScalar("cellphoneNO", StringType.INSTANCE)
							.addScalar("imageUrl", StringType.INSTANCE)
							.addScalar("fromPlayerId", LongType.INSTANCE)
							.addScalar("playerId", LongType.INSTANCE)
							.addScalar("status", StringType.INSTANCE)
							.setResultTransformer(Transformers.aliasToBean(UserWithStatus.class))
							.setParameter("currentUserId", currentUserId)
							.setParameter("criteria", "%" + criteria + "%");
		
		
		@SuppressWarnings("unchecked")
		List<UserWithStatus> userList = (List<UserWithStatus>) query.list(); 
		for(UserWithStatus u: userList){
			setUserStatus(currentUserId, u);
		}
		return userList;
	}
	
	private UserWithStatus setUserStatus(Long currentUserId, UserWithStatus u){
		if(ObjectUtil.isNotNull(u)){
			if(u.getStatus() == null || "".equals(u.getStatus())){
				if(u.getId().equals(currentUserId)){
					u.setStatus("yourself");
				}else{
					u.setStatus("not-friend");
				}
				
			}else{
				if(u.getStatus().equals("APPLY") && u.getFromPlayerId().equals(currentUserId)){
					u.setStatus("you-invited-no-reply");
				}
				if(u.getStatus().equals("APPLY") && u.getPlayerId().equals(currentUserId)){
					u.setStatus("he-invited-no-reply");
				}
				if(u.getStatus().equals("REFUSE") && u.getFromPlayerId().equals(currentUserId)){
					u.setStatus("you-invited-refuse");
				}
				if(u.getStatus().equals("REFUSE") && u.getPlayerId().equals(currentUserId)){
					u.setStatus("he-invited-refuse");
				}
				if(u.getStatus().equals("ACCEPT")){
					u.setStatus("friends");
				}
			}
			return u;
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getAllUsersCellPhones() {

		String sql = "select name from account";
		Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return query.list();
	}
}
