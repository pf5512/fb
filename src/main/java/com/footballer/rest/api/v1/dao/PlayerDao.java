package com.footballer.rest.api.v1.dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.rest.api.v1.domain.PlayerBaseInfo;
import com.footballer.rest.api.v1.domain.PlayerSkillPropertyRanking;
import com.footballer.rest.api.v1.domain.PlayerSkillPropertyTeamRanking;
import com.footballer.rest.api.v1.domain.TeamBaseInfo;
import com.footballer.rest.api.v1.exception.CopyException;
import com.footballer.rest.api.v1.result.PlayerWithTeamBaseInfoResult;
import com.footballer.rest.api.v1.vo.EventInvitation;
import com.footballer.rest.api.v1.vo.Player;
import com.footballer.rest.api.v1.vo.PlayerAppToken;
import com.footballer.rest.api.v1.vo.PlayerFriend;
import com.footballer.rest.api.v1.vo.PlayerProperty;
import com.footballer.rest.api.v1.vo.Team;
import com.footballer.rest.api.v1.vo.enumType.InvitationResponseType;
import com.footballer.util.CommonUtil;
import com.footballer.util.CopyUtil;
import com.footballer.util.ObjectUtil;
import com.google.common.collect.Lists;

/**
 * Created by justin on 20150605
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
public class PlayerDao extends BaseDao {
	
	@Autowired
	public EvaluationDao evaluationDao;
	
	
	
    public Player findPlayer(Long id) {
        Player player = findById(Player.class, id);
        if (null != player) {
            player.setTeams(player.getTeams());
        }
        return player;
    }
    
    public Player findJustPlayer(Long id) {
    	Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Player.class);
    	criteria.add(Restrictions.eq("id", id));
        return  (Player) criteria.uniqueResult();
    }
    
    public boolean checkPlayersAllExist(List<Long> playerIds)
    {
    	Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Player.class);
    	criteria.add(Restrictions.in("id", playerIds));
    	criteria.setProjection(Projections.rowCount());  
		if(playerIds.size() != Integer.parseInt(criteria.uniqueResult().toString())){
			return false;
		}
    	return true;
    }
    
    public boolean checkPlayersIsNotFriends(Long playerId, Long friendPlayerId){
    	//只需要单边检查 因为插入时 是双向插入 详见 addPlayerAsFriend（）
    	Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PlayerFriend.class);
    	criteria.add(Restrictions.eq("playerId", playerId))
    			.add(Restrictions.eq("friendPlayerId", friendPlayerId));

    	if(criteria.list().size() > 0){
    		return false;
    	}else{
    		return true;
    	}
    }
    
    public void addPlayerAsFriend(Long playerId, Long friendPlayerId) {
    	if(checkPlayersIsNotFriends(playerId, friendPlayerId)){
    		PlayerFriend pf = new PlayerFriend();
        	pf.init();
        	pf.setPlayerId(playerId);
        	pf.setFriendPlayerId(friendPlayerId);
        	pf.setStatus(PlayerFriend.PLAYER_STATUS.ACTIVE.toString());
        	save(pf);
        	
        	pf = new PlayerFriend();
        	pf.init();
        	pf.setPlayerId(friendPlayerId);
        	pf.setFriendPlayerId(playerId);
        	pf.setStatus(PlayerFriend.PLAYER_STATUS.ACTIVE.toString());
        	save(pf);
    	}
    }

    public PlayerProperty findPlayerProperty(Long id) {
    	Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PlayerProperty.class);
    	criteria.add(Restrictions.eq("id", id));
        return (PlayerProperty) criteria.uniqueResult();
    }

   public void updatePlayerProperty(PlayerProperty playerProperty) 
    {
        CommonUtil.addAuditableAttributes(playerProperty);
        Long accountId = playerProperty.getAccount().getId();
        PlayerProperty currentPlayerProperty = findPlayerProperty(accountId);
        if(ObjectUtil.isNotNull(currentPlayerProperty)){
        	currentPlayerProperty.setBirth(playerProperty.getBirth());
        	currentPlayerProperty.setHeavyFoot(playerProperty.getHeavyFoot());
        	currentPlayerProperty.setWeight(playerProperty.getWeight());
        	currentPlayerProperty.setHeight(playerProperty.getHeight());
        	currentPlayerProperty.setUpdateBy(playerProperty.getUpdateBy());
        	currentPlayerProperty.setUpdateDt(playerProperty.getUpdateDt());
        	update(currentPlayerProperty);
        } else {
        	//临时解决方案，因为之前一些数据没有 property 属性 所以 新版本上去会出现脏数据，先 帮这些数据自动创建
        	save(playerProperty);
        }
    } 

    @SuppressWarnings({ "unchecked", "static-access" })
	public List<Player> findAllPlayers() {
    	 Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Player.class);
    	 criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY); 
    	 criteria.addOrder(Order.asc("id"));
         return criteria.list();
    }
    
    @SuppressWarnings("unchecked")
	public List<PlayerAppToken> findPlayerAllTokens(Long playerId) {
    	Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PlayerAppToken.class);
    	criteria.add(Restrictions.eq("playerId", playerId));
    	
    	return criteria.list();
    }

	@SuppressWarnings("static-access")
	public List<PlayerWithTeamBaseInfoResult> getPlayerFriendsList(Long playerId) throws CopyException{
		List<PlayerWithTeamBaseInfoResult> list = Lists.newArrayList();
		
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PlayerFriend.class);
		criteria.add(Restrictions.eq("playerId", playerId))
        		     .addOrder(Order.desc("createDt"))
        		     .setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<PlayerFriend> playerFriends = criteria.list();
		 
		for(PlayerFriend pf : playerFriends)
		{
			PlayerWithTeamBaseInfoResult result = new PlayerWithTeamBaseInfoResult();
			PlayerBaseInfo playerBaseInfo = new PlayerBaseInfo();
        	List<TeamBaseInfo> teamBaseInfoList = Lists.newArrayList();
        	
        	Player player;
        	player = findPlayer(pf.getFriendPlayerId());
        	CopyUtil.copy(playerBaseInfo, player);
        	
        	if(ObjectUtil.isNotNull(player)){
        		Set<Team> teams =  player.getTeams();
            	for(Team t: teams){
            		TeamBaseInfo teamBaseInfo = new TeamBaseInfo();
            		CopyUtil.copy(teamBaseInfo, t);
            		teamBaseInfoList.add(teamBaseInfo);
            	}
            	result.setPlayer(playerBaseInfo);
        		result.setTeamList(teamBaseInfoList);
        		list.add(result);
        	}
    		
		} 
		return list;
	}
	
	public List<PlayerBaseInfo> getPlayerFriendsBaseInfoList(Long playerId) throws CopyException{
		List<PlayerBaseInfo> list = Lists.newArrayList();
		
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PlayerFriend.class);
		criteria.add(Restrictions.eq("playerId", playerId))
        		     .addOrder(Order.desc("createDt"));
		@SuppressWarnings("unchecked")
		List<PlayerFriend> playerFriends = criteria.list();
		 
		for(PlayerFriend pf : playerFriends)
		{
			Player player = findJustPlayer(pf.getFriendPlayerId());
			PlayerBaseInfo playerBaseInfo = new PlayerBaseInfo();
        	CopyUtil.copy(playerBaseInfo, player);
			list.add(playerBaseInfo);
		} 
		return list;
	}
	
	//获取已加入当前赛事的队员名单
	public List<PlayerBaseInfo> getJoinedEventPlayerList(Long eventId) throws CopyException{
		List<PlayerBaseInfo> list = Lists.newArrayList();
		
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(EventInvitation.class);
		criteria.add(Restrictions.eq("eventId", eventId))
					.add(Restrictions.or(
								Restrictions.eq("status", InvitationResponseType.OWNER)
								,Restrictions.eq("status", InvitationResponseType.ACCEPT))
							);
		@SuppressWarnings("unchecked")
		List<EventInvitation> JoinedList = criteria.list();
		for(EventInvitation ei: JoinedList)
		{
			Player player = findJustPlayer(ei.getPlayerId());
			PlayerBaseInfo playerBaseInfo = new PlayerBaseInfo();
        	CopyUtil.copy(playerBaseInfo, player);
			list.add(playerBaseInfo);
		}
		return list;
	}
	
	public List<PlayerSkillPropertyRanking> getPlayerFriendsRankingOfSkillType_v2(String skillType, Long playerId) throws CopyException{
		List<PlayerSkillPropertyRanking> list = Lists.newArrayList();
		String sql = "select  fp.id as palyerId,fp.nick_name as nickName,fp.imageUrl,pmeac.number "
					+" from football_player fp left join player_mutual_evaluation_all_count pmeac "
					+" on (fp.id = pmeac.player_id and( pmeac.property_type = :skillType or pmeac.property_type is null)) "
					+" where exists(select 1 from player_friend pf where pf.friend_player_id = fp.id and (pf.player_id = :palyerId or fp.id = :palyerId ))	"
					+" order by pmeac.number desc ";
		Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addScalar("palyerId", LongType.INSTANCE)
				.addScalar("nickName", StringType.INSTANCE)
				.addScalar("imageUrl", StringType.INSTANCE)
				.addScalar("number", IntegerType.INSTANCE)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setParameter("palyerId", playerId)
				.setParameter("skillType", skillType);
		@SuppressWarnings("unchecked")
    	List<Map<String,Object>> resultList= query.list();
		PlayerSkillPropertyRanking pspr = null;
		int ranking = 0;
		for(Map<String,Object> map:resultList){
			ranking++;
			pspr = this.buildPlayersSkillPropertyRanking(ranking, map);
			list.add(pspr);
		}
		return list;
	}
	
	//获取球员的某个技术指标在好友中的排名
	public List<PlayerSkillPropertyRanking> getPlayerFriendsRankingOfSkillType(String skillType, Long playerId) throws CopyException{
		List<PlayerSkillPropertyRanking> list = Lists.newArrayList();
		
		Player currentPlayer = findJustPlayer(playerId);
		Integer totalNumber = evaluationDao.findPlayerOneSkillRecognisedTotalNumber(skillType, playerId);
		PlayerSkillPropertyRanking pspr = new PlayerSkillPropertyRanking();
		pspr.setPlayerId(playerId);
		pspr.setPlayerName(currentPlayer.getNickName());
		pspr.setPlayerIconUrl(currentPlayer.getImageUrl());
		pspr.setTotalNumber(totalNumber);
		
		//TODO
		//pspr.setCityRanking(cityRanking);
		//pspr.setProvinceRanking(provinceRanking);
		//pspr.setNationRanking(nationRanking);
		list.add(pspr);
		
		List<PlayerBaseInfo> currentPlayersFriendsBaseInfoList = getPlayerFriendsBaseInfoList(playerId);
		for(PlayerBaseInfo p: currentPlayersFriendsBaseInfoList)
		{
			Integer total = evaluationDao.findPlayerOneSkillRecognisedTotalNumber(skillType, p.getId());
			
			PlayerSkillPropertyRanking playerSkillPropertyRanking = new PlayerSkillPropertyRanking();
			playerSkillPropertyRanking.setPlayerId(p.getId());
			playerSkillPropertyRanking.setPlayerName(p.getNickName());
			playerSkillPropertyRanking.setPlayerIconUrl(p.getImageUrl());
			playerSkillPropertyRanking.setTotalNumber(total);
			
			//TODO
			//pspr.setCityRanking(cityRanking);
			//pspr.setProvinceRanking(provinceRanking);
			//pspr.setNationRanking(nationRanking);
			list.add(playerSkillPropertyRanking);
		}
		
		//sort by 认同总次数排序
    	Collections.sort(list,new Comparator<PlayerSkillPropertyRanking>(){  
            @Override  
            public int compare(PlayerSkillPropertyRanking p1,PlayerSkillPropertyRanking p2) {  
                return p2.getTotalNumber() - p1.getTotalNumber();  
            }  
        });  
    	
    	//设置好友列表中对应的名次
    	for(int i=0;i< list.size();i++)
    	{
    		list.get(i).setRanking(i+1);
    	}
		return list;
	}
	
	//获取球员的某个技术指标在他所在的球队中的排名_v2
	public List<PlayerSkillPropertyTeamRanking> getPlayerTeamsRankingOfSkillType_v2(String skillType, Long playerId){
		List<PlayerSkillPropertyTeamRanking> list = Lists.newArrayList();
		String sql = "select tp.team_id as teamId,t.name,t.logo ,fp.id as palyerId,fp.nick_name as nickName , fp.imageUrl as imageUrl ,pmeac.number "
						+" from team_player tp left join player_mutual_evaluation_all_count pmeac " +
						"                     on (tp.player_id =  pmeac.player_id and (pmeac.property_type = :skillType or pmeac.property_type is null ))," +
						"		football_player fp,team t "
						+" where  tp.player_id = fp.id "
						+" and tp.team_id = t.id "
						+" and  exists(select 1 from team_player tp2 where tp.team_id = tp2.team_id and tp2.player_id = :palyerId) "
						+" order by tp.team_id,pmeac.number desc ,pmeac.player_id desc ";
		Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addScalar("teamId",  LongType.INSTANCE)
				.addScalar("name", StringType.INSTANCE)
				.addScalar("logo", StringType.INSTANCE)
				.addScalar("nickName", StringType.INSTANCE)
				.addScalar("imageUrl", StringType.INSTANCE)
				.addScalar("number", IntegerType.INSTANCE)
				.addScalar("palyerId", LongType.INSTANCE)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setParameter("palyerId", playerId)
				.setParameter("skillType", skillType);
		@SuppressWarnings("unchecked")
    	List<Map<String,Object>> resultList= query.list();
		PlayerSkillPropertyTeamRanking psptr = null;
		PlayerSkillPropertyRanking pspr = null;
		int ranking = 0;
		Long lastTeamId = null; 
		Long teamId = null; 
		for(Map<String,Object> map : resultList){
			teamId = (Long)map.get("teamId");
			if(lastTeamId == null || lastTeamId.longValue() !=teamId.longValue()){
				psptr = new PlayerSkillPropertyTeamRanking();
				psptr.setTeamIconUrl((String)map.get("logo"));
				psptr.setTeamId(teamId);
				psptr.setTeamName((String)map.get("name"));
				ranking++;
				
				List<PlayerSkillPropertyRanking> pList =  Lists.newArrayList();
				pspr = buildPlayersSkillPropertyRanking(ranking, map);
				pList.add(pspr);

				psptr.setTeamMembersRankingList(pList);
				list.add(psptr);
			}else{
				ranking++;
				pspr = buildPlayersSkillPropertyRanking(ranking, map);
				psptr.getTeamMembersRankingList().add(pspr);
			}
			lastTeamId =  teamId;
		}
		return list;
	}

	private PlayerSkillPropertyRanking buildPlayersSkillPropertyRanking(
			int ranking, Map<String, Object> map) {
		PlayerSkillPropertyRanking pspr;
		pspr = new PlayerSkillPropertyRanking();
		pspr.setPlayerId((Long)map.get("palyerId"));
		pspr.setPlayerName((String)map.get("nickName"));
		pspr.setPlayerIconUrl((String)map.get("imageUrl"));
		pspr.setTotalNumber((Integer)(map.get("number")));
		if(pspr.getTotalNumber() == null){
			pspr.setTotalNumber(0);
		}
		pspr.setRanking(ranking);
		return pspr;
	}
	
	//获取球员的某个技术指标在他所在的球队中的排名
	public List<PlayerSkillPropertyTeamRanking> getPlayerTeamsRankingOfSkillType(String skillType, Long playerId){
		List<PlayerSkillPropertyTeamRanking> list = Lists.newArrayList();
		
		Player currentPlayer = findPlayer(playerId);
		Set<Team> teams = currentPlayer.getTeams();
		
		for(Team t:teams)
		{
			PlayerSkillPropertyTeamRanking psptr = new PlayerSkillPropertyTeamRanking();
			psptr.setTeamId(t.getId());
			psptr.setTeamName(t.getName());
			psptr.setTeamIconUrl(t.getLogo());
			
			List<PlayerSkillPropertyRanking> pList =  Lists.newArrayList();
			Set<Player> players = t.getPlayers();
			for(Player p:players)
			{
				Integer total = evaluationDao.findPlayerOneSkillRecognisedTotalNumber(skillType, p.getId());
				
				PlayerSkillPropertyRanking playerSkillPropertyRanking = new PlayerSkillPropertyRanking();
				playerSkillPropertyRanking.setPlayerId(p.getId());
				playerSkillPropertyRanking.setPlayerName(p.getNickName());
				playerSkillPropertyRanking.setPlayerIconUrl(p.getImageUrl());
				playerSkillPropertyRanking.setTotalNumber(total);
				
				pList.add(playerSkillPropertyRanking);
			}
			//sort by 认同总次数排序
	    	Collections.sort(pList,new Comparator<PlayerSkillPropertyRanking>(){  
	            @Override  
	            public int compare(PlayerSkillPropertyRanking p1,PlayerSkillPropertyRanking p2) {  
	                return p2.getTotalNumber() - p1.getTotalNumber();  
	            }  
	        });  
	    	//设置队友列表中对应的名次
	    	for(int i=0;i< pList.size();i++)
	    	{
	    		pList.get(i).setRanking(i+1);
	    	}
			psptr.setTeamMembersRankingList(pList);
			list.add(psptr);
		}
		
		return list;
	}
	
}
