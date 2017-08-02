package com.footballer.rest.api.v1.dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.rest.api.v1.domain.PlayerBaseInfo;
import com.footballer.rest.api.v1.domain.PlayerRecognisedRecord;
import com.footballer.rest.api.v1.domain.TeamBaseInfo;
import com.footballer.rest.api.v1.exception.CopyException;
import com.footballer.rest.api.v1.result.InvitationResult;
import com.footballer.rest.api.v1.vo.Player;
import com.footballer.rest.api.v1.vo.PlayerPlayerInvitation;
import com.footballer.rest.api.v1.vo.Team;
import com.footballer.rest.api.v1.vo.TeamPlayerInvitation;
import com.footballer.rest.api.v1.vo.enumType.InvitationMessageType;
import com.footballer.rest.api.v1.vo.enumType.InvitationResponseType;
import com.footballer.rest.api.v1.vo.enumType.InvitationType;
import com.footballer.util.CommonUtil;
import com.footballer.util.CopyUtil;
import com.footballer.util.ObjectUtil;
import com.google.common.collect.Lists;


public class InvitationDao extends BaseDao {
	
	@Autowired
	public TeamDao teamDao;
	
	@Autowired
	public UserDao userDao;
	
	@Autowired
	public PlayerDao playerDao;
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void createPlayerToTeam(TeamPlayerInvitation tpInvitation) {		
		CommonUtil.addAuditableAttributes(tpInvitation);
		tpInvitation.setRequestTime(new Date());
		tpInvitation.setStatus(InvitationResponseType.APPLY);
		tpInvitation.setType(InvitationType.PLAYERTOTEAM);
        saveOrUpdate(tpInvitation);
        flush();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void PlayerToPlayer(PlayerPlayerInvitation ppi) {		
		CommonUtil.addAuditableAttributes(ppi);
		ppi.setRequestTime(new Date());
		ppi.setStatus(InvitationResponseType.APPLY);
        saveOrUpdate(ppi);
        flush();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void TeamInvitePlayers(Long teamId, List<Long> playerIds) {		
		
		// TODO: use batch insert
		for(Long playerId : playerIds){
			TeamPlayerInvitation invitation = new TeamPlayerInvitation();
			invitation.setTeamId(teamId);
			invitation.setPlayerId(playerId);
			CommonUtil.addAuditableAttributes(invitation);
			invitation.setRequestTime(new Date());
			invitation.setStatus(InvitationResponseType.APPLY);
			invitation.setType(InvitationType.TEAMTOPLAYER);
	        saveOrUpdate(invitation);
	        flush();
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public boolean checkTeamPlayerInvitationisNotExist(TeamPlayerInvitation tpi){
		
		switch(tpi.getType()){
		case PLAYERTOTEAM:
			if(ObjectUtil.isNotNull(findPlayerTOTeam(tpi.getPlayerId(), tpi.getTeamId()))) return false;
			break;
		case TEAMTOPLAYER:
			if(ObjectUtil.isNotNull(findTeamTOPlayer(tpi.getTeamId(), tpi.getPlayerId()))) return false;
			break;
		default:
			break;
		}
		return true;
	} 
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public String getTeamPlayerInvitationStatus(Long teamId, Long playerId){
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(TeamPlayerInvitation.class);
		criteria.add(Restrictions.eq("playerId", playerId))
				.add(Restrictions.eq("teamId", teamId));
		TeamPlayerInvitation  tpInvitation= (TeamPlayerInvitation) criteria.uniqueResult();
		if(ObjectUtil.isNotNull(tpInvitation)){
			return  tpInvitation.getStatus().toString();
		}else{
			return  "NOT MEMBER";
		}
	} 
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public PlayerPlayerInvitation findPlayerTOPlayer(Long fromPlayerId,Long toPlayerId){
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PlayerPlayerInvitation.class);
		criteria.add(Restrictions.eq("fromPlayerId", fromPlayerId))
				.add(Restrictions.eq("playerId", toPlayerId));
		PlayerPlayerInvitation tpInvitation = (PlayerPlayerInvitation) criteria.uniqueResult();
		
		Criteria criteria2 = getSessionFactory().getCurrentSession().createCriteria(PlayerPlayerInvitation.class);
		criteria2.add(Restrictions.eq("fromPlayerId", toPlayerId))
				.add(Restrictions.eq("playerId", fromPlayerId));
		PlayerPlayerInvitation tpInvitation2 = (PlayerPlayerInvitation) criteria2.uniqueResult();
		
		if(ObjectUtil.isNotNull(tpInvitation)){
			return tpInvitation;
		}else if(ObjectUtil.isNotNull(tpInvitation2)){
			return tpInvitation2;
		}else{
			return null;
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public TeamPlayerInvitation findPlayerTOTeam(Long fromPlayerId,Long teamId){
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(TeamPlayerInvitation.class);
		criteria.add(Restrictions.eq("playerId", fromPlayerId))
				.add(Restrictions.eq("teamId", teamId))
				.add(Restrictions.eq("type", InvitationType.PLAYERTOTEAM));
		TeamPlayerInvitation tpInvitation = (TeamPlayerInvitation) criteria.uniqueResult();
		return tpInvitation;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public TeamPlayerInvitation findTeamTOPlayer(Long teamId, Long toPlayerId){
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(TeamPlayerInvitation.class);
		criteria.add(Restrictions.eq("teamId", teamId))
				.add(Restrictions.eq("playerId", toPlayerId))
				.add(Restrictions.eq("type", InvitationType.TEAMTOPLAYER));
		TeamPlayerInvitation tpInvitation = (TeamPlayerInvitation) criteria.uniqueResult();
		return tpInvitation;
	}
		
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public boolean approveMentTeamPlayer(TeamPlayerInvitation tpInvitation, InvitationType type, boolean isApproved){
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(TeamPlayerInvitation.class);
		criteria.add(Restrictions.eq("type", type));
		criteria.add(Restrictions.eq("playerId", tpInvitation.getPlayerId()));
		criteria.add(Restrictions.eq("teamId", tpInvitation.getTeamId()));
		
		TeamPlayerInvitation invitation =  (TeamPlayerInvitation) criteria.uniqueResult();
		if(ObjectUtil.isNotNull(invitation)){
			if(isApproved){
				invitation.setStatus(InvitationResponseType.ACCEPT);
				invitation.setResponseTime(new Date());
			}else{
				invitation.setStatus(InvitationResponseType.REFUSE);
				invitation.setResponseTime(new Date());
			}
			saveOrUpdate(invitation);
	        flush();
			return true;
		}
		return false;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public boolean approveMentPlayer2Player(PlayerPlayerInvitation ppi,  boolean isApproved){
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PlayerPlayerInvitation.class);
		criteria.add(Restrictions.eq("fromPlayerId", ppi.getFromPlayerId()));
		criteria.add(Restrictions.eq("playerId", ppi.getPlayerId()));
		PlayerPlayerInvitation invitation =  (PlayerPlayerInvitation) criteria.uniqueResult();
		
		if(ObjectUtil.isNotNull(invitation)){
			CommonUtil.addAuditableAttributes(invitation);
			invitation.setResponseTime(new Date());
			if(isApproved){
				invitation.setStatus(InvitationResponseType.ACCEPT);
			}else{
				invitation.setStatus(InvitationResponseType.REFUSE);
			}
			saveOrUpdate(invitation);
	        flush();
			return true;
		}
		return false;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public boolean updateTeamPlayerInvitation(TeamPlayerInvitation tpInvitation) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(TeamPlayerInvitation.class);
		criteria.add(Restrictions.eq("teamId", tpInvitation.getTeamId()))
				.add(Restrictions.eq("playerId", tpInvitation.getPlayerId()));
		
		TeamPlayerInvitation invitation =  (TeamPlayerInvitation) criteria.uniqueResult();
		if(ObjectUtil.isNotNull(invitation)){
			InvitationResponseType status = invitation.getStatus();
			if(status != null || "".equals(status)){
				invitation.setStatus(status);
				invitation.setResponseTime(new Date());
			}
			saveOrUpdate(invitation);
	        flush();
			return true;
		}
		return false;
	}
	
	//TODO 随着时间的推移 需要加入 时间分段限制 不然数据量太大
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<InvitationResult> getAllInvitationList(Long playerId) throws CopyException{
		List<InvitationResult> invitations = Lists.newArrayList();
		invitations.addAll(getSentInvitationList(playerId));
		invitations.addAll(getReceivedInvitationList(playerId));
		return invitations;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<InvitationResult> getSentInvitationList(Long playerId) throws CopyException{
		List<InvitationResult> invitations = Lists.newArrayList();
		
		//发出 球员申请加入球队的请求
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(TeamPlayerInvitation.class);
        criteria.add(Restrictions.eq("playerId", playerId))
                     .add(Restrictions.eq("type", InvitationType.PLAYERTOTEAM));
        criteria.addOrder(Order.desc("requestTime"));
        @SuppressWarnings("unchecked")
		List<TeamPlayerInvitation> tpInvitations = criteria.list();
		
        for(TeamPlayerInvitation tpi: tpInvitations){
        	InvitationResult invitationResult = new InvitationResult();
        	
        	PlayerBaseInfo fromPlayerBaseInfo = new PlayerBaseInfo();
        	TeamBaseInfo teamBaseInfo = new TeamBaseInfo();
        	
        	Player fromPlayer = playerDao.findPlayer(tpi.getPlayerId());
        	CopyUtil.copy(fromPlayerBaseInfo, fromPlayer);
        	invitationResult.setFromPlayer(fromPlayerBaseInfo);
        	
        	Team team = teamDao.findById(Team.class, tpi.getTeamId());
        	CopyUtil.copy(teamBaseInfo, team);
        	invitationResult.setTeam(teamBaseInfo);
        	
        	invitationResult.setTeamPlayerInvitation(tpi);
        	invitationResult.setMessageType(InvitationMessageType.SENDER);
        	
        	invitations.add(invitationResult);
        }
        
      //发出 邀请球员加入球队的请求
        Criteria criteriaTeam = getSessionFactory().getCurrentSession().createCriteria(TeamPlayerInvitation.class);
        criteriaTeam.add(Restrictions.eq("createBy", playerId.toString()))
                     .add(Restrictions.eq("type", InvitationType.TEAMTOPLAYER));
        criteriaTeam.addOrder(Order.desc("requestTime"));
        @SuppressWarnings("unchecked")
		List<TeamPlayerInvitation> tpiLists = criteriaTeam.list();
		
        for(TeamPlayerInvitation tpi: tpiLists){
        	InvitationResult invitationResult = new InvitationResult();
        	
        	TeamBaseInfo teamBaseInfo = new TeamBaseInfo();
        	PlayerBaseInfo toPlayerBaseInfo = new PlayerBaseInfo();
        	
        	Player toPlayer = playerDao.findPlayer(tpi.getPlayerId());
        	CopyUtil.copy(toPlayerBaseInfo, toPlayer);
        	invitationResult.setToPlayer(toPlayerBaseInfo);
        	
        	Team team = teamDao.findById(Team.class, tpi.getTeamId());
        	CopyUtil.copy(teamBaseInfo, team);
        	invitationResult.setTeam(teamBaseInfo);
        	
        	invitationResult.setTeamPlayerInvitation(tpi);
        	invitationResult.setMessageType(InvitationMessageType.SENDER);
        	
        	invitations.add(invitationResult);
        }
        
      //发出 球员申请加其他球员好友
        Criteria criteriaPlayer = getSessionFactory().getCurrentSession().createCriteria(PlayerPlayerInvitation.class);
        criteriaPlayer.add(Restrictions.eq("fromPlayerId", playerId));
        criteriaPlayer.addOrder(Order.desc("requestTime"));
        @SuppressWarnings("unchecked")
		List<PlayerPlayerInvitation> ppInvitations = criteriaPlayer.list();
		
        for(PlayerPlayerInvitation ppi: ppInvitations){
        	InvitationResult invitationResult = new InvitationResult();
        	
        	PlayerBaseInfo fromPlayerBaseInfo = new PlayerBaseInfo();
        	PlayerBaseInfo toPlayerBaseInfo = new PlayerBaseInfo();
        	
        	Player fromPlayer = playerDao.findPlayer(ppi.getFromPlayerId());
        	CopyUtil.copy(fromPlayerBaseInfo, fromPlayer);
        	invitationResult.setFromPlayer(fromPlayerBaseInfo);
        	
        	Player toPlayer = playerDao.findPlayer(ppi.getPlayerId());
        	CopyUtil.copy(toPlayerBaseInfo, toPlayer);
        	invitationResult.setToPlayer(toPlayerBaseInfo);
        	
        	invitationResult.setPlayerPlayerInvitation(ppi);
        	invitationResult.setMessageType(InvitationMessageType.SENDER);
        	
        	invitations.add(invitationResult);
        }
        
      //sort by 认请求时间
        Collections.sort(invitations,new Comparator<InvitationResult>(){  
            @Override  
            public int compare(InvitationResult r1,InvitationResult r2) {  
                if(r1.getPlayerPlayerInvitation()!= null &&  r2.getPlayerPlayerInvitation()!= null)
                {
                	return  r2.getPlayerPlayerInvitation().getRequestTime().getTime() >  r1.getPlayerPlayerInvitation().getRequestTime().getTime()? 1 :-1;  
                }else if(r1.getTeamPlayerInvitation()!= null &&  r2.getTeamPlayerInvitation()!= null)
                {
                	return r2.getTeamPlayerInvitation().getRequestTime().getTime() >  r1.getTeamPlayerInvitation().getRequestTime().getTime()? 1: -1;  
                }else if(r1.getPlayerPlayerInvitation()!= null &&  r2.getTeamPlayerInvitation()!= null)
                {
                	return r2.getTeamPlayerInvitation().getRequestTime().getTime() >  r1.getPlayerPlayerInvitation().getRequestTime().getTime()? 1: -1;  
                }else if(r1.getTeamPlayerInvitation()!= null &&  r2.getPlayerPlayerInvitation()!= null)
                {
                	return r2.getPlayerPlayerInvitation().getRequestTime().getTime() > r1.getTeamPlayerInvitation().getRequestTime().getTime()? 1: -1;
                }else{
                	return 0;
                }
            }  
        });  
        
		return invitations;
	}
	
	public List<InvitationResult> getReceivedInvitationList(Long playerId) throws CopyException{
		List<InvitationResult> invitations = Lists.newArrayList();
		
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(TeamPlayerInvitation.class);
        criteria.add(Restrictions.eq("playerId", playerId))
                    .add(Restrictions.eq("type", InvitationType.TEAMTOPLAYER));
        criteria.addOrder(Order.desc("requestTime"));
        @SuppressWarnings("unchecked")
		List<TeamPlayerInvitation> tpInvitations = criteria.list();
		
        for(TeamPlayerInvitation tpi: tpInvitations){
        	InvitationResult invitationResult = new InvitationResult();
        	
        	PlayerBaseInfo toPlayerBaseInfo = new PlayerBaseInfo();
        	TeamBaseInfo teamBaseInfo = new TeamBaseInfo();
        	
        	Player toPlayer = playerDao.findPlayer(tpi.getPlayerId());
        	CopyUtil.copy(toPlayerBaseInfo, toPlayer);
        	invitationResult.setToPlayer(toPlayerBaseInfo);
        	
        	Team team = teamDao.findById(Team.class, tpi.getTeamId());
        	CopyUtil.copy(teamBaseInfo, team);
        	invitationResult.setTeam(teamBaseInfo);
        	
        	invitationResult.setTeamPlayerInvitation(tpi);
        	invitationResult.setMessageType(InvitationMessageType.RECEIVER);
        	//收到 球队邀请球员加入的邀请
        	invitations.add(invitationResult);
        }
        Criteria criteriaPlayer = getSessionFactory().getCurrentSession().createCriteria(PlayerPlayerInvitation.class);
        criteriaPlayer.add(Restrictions.eq("playerId", playerId));
        criteriaPlayer.addOrder(Order.desc("requestTime"));
        @SuppressWarnings("unchecked")
		List<PlayerPlayerInvitation> ppInvitations = criteriaPlayer.list();
		
        for(PlayerPlayerInvitation ppi: ppInvitations){
        	InvitationResult invitationResult = new InvitationResult();
        	
        	PlayerBaseInfo fromPlayerBaseInfo = new PlayerBaseInfo();
        	PlayerBaseInfo toPlayerBaseInfo = new PlayerBaseInfo();
        	
        	Player fromPlayer = playerDao.findPlayer(ppi.getFromPlayerId());
        	CopyUtil.copy(fromPlayerBaseInfo, fromPlayer);
        	invitationResult.setFromPlayer(fromPlayerBaseInfo);
        	
        	Player toPlayer = playerDao.findPlayer(ppi.getPlayerId());
        	CopyUtil.copy(toPlayerBaseInfo, toPlayer);
        	invitationResult.setToPlayer(toPlayerBaseInfo);
        	
        	invitationResult.setPlayerPlayerInvitation(ppi);
        	invitationResult.setMessageType(InvitationMessageType.RECEIVER);
        	//收到 球员申请加好友的邀请
        	invitations.add(invitationResult);
        }
        
        //加到一起后任然需要按时间排序，因为之前是各自排序的
//        Collections.sort(invitations,new Comparator<InvitationResult>(){  
//            @Override  
//            public int compare(InvitationResult r1,InvitationResult r2) {  
//            	if(ObjectUtil.isNotNull(r1.getPlayerPlayerInvitation()) && ObjectUtil.isNotNull(r2.getPlayerPlayerInvitation())){
//            		if( r2.getPlayerPlayerInvitation().getRequestTime().getTime() > r1.getPlayerPlayerInvitation().getRequestTime().getTime()){
//            			return 1;
//            		}else{
//            			return -1;
//            		} 
//            	}else if(ObjectUtil.isNotNull(r1.getTeamPlayerInvitation()) && ObjectUtil.isNotNull(r2.getTeamPlayerInvitation())){
//            		if( r2.getTeamPlayerInvitation().getRequestTime().getTime() > r1.getTeamPlayerInvitation().getRequestTime().getTime()){
//            			return 1;
//            		}else{
//            			return -1;
//            		} 
//            	}else if(ObjectUtil.isNotNull(r1.getPlayerPlayerInvitation()) && ObjectUtil.isNotNull(r2.getTeamPlayerInvitation())){
//            		if( r2.getTeamPlayerInvitation().getRequestTime().getTime() > r1.getPlayerPlayerInvitation().getRequestTime().getTime()){
//            			return 1;
//            		}else{
//            			return -1;
//            		} 
//            	}else if(ObjectUtil.isNotNull(r1.getTeamPlayerInvitation()) && ObjectUtil.isNotNull(r2.getPlayerPlayerInvitation())){
//            		if( r2.getPlayerPlayerInvitation().getRequestTime().getTime() > r1.getTeamPlayerInvitation().getRequestTime().getTime()){
//            			return 1;
//            		}else{
//            			return -1;
//            		} 
//            	}else{
//            		return 0;
//            	}
//            	
//            }  
//        });  
        
        
		return invitations;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<InvitationResult> getAllPlayerFriendsInvitationList(Long playerId) throws CopyException{
		List<InvitationResult> invitations = Lists.newArrayList();
		invitations.addAll(getSentPlayerFriendsInvitationList(playerId));
		invitations.addAll(getReceivedPlayerFriendsInvitationList(playerId));
		return invitations;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<InvitationResult> getSentPlayerFriendsInvitationList(Long playerId) throws CopyException{
		List<InvitationResult> invitations = Lists.newArrayList();
		
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PlayerPlayerInvitation.class);
        criteria.add(Restrictions.eq("fromPlayerId", playerId));
        criteria.addOrder(Order.desc("requestTime"));
        
        @SuppressWarnings("unchecked")
		List<PlayerPlayerInvitation> ppiLists = criteria.list();
		
        for(PlayerPlayerInvitation ppi: ppiLists){
        	InvitationResult invitationResult = new InvitationResult();
        	PlayerBaseInfo fromPlayerBaseInfo = new PlayerBaseInfo();
        	PlayerBaseInfo toPlayerBaseInfo = new PlayerBaseInfo();
        	
        	Player fromPlayer = playerDao.findPlayer(ppi.getFromPlayerId());
        	CopyUtil.copy(fromPlayerBaseInfo, fromPlayer);
        	invitationResult.setFromPlayer(fromPlayerBaseInfo);
        	
        	Player toPlayer = playerDao.findPlayer(ppi.getPlayerId());
            CopyUtil.copy(toPlayerBaseInfo, toPlayer);
            invitationResult.setToPlayer(toPlayerBaseInfo);
        	
        	invitationResult.setPlayerPlayerInvitation(ppi);
        	invitationResult.setMessageType(InvitationMessageType.SENDER);
        	invitations.add(invitationResult);
        }
		return invitations;
	}
	
	public List<InvitationResult> getReceivedPlayerFriendsInvitationList(Long playerId) throws CopyException{
		List<InvitationResult> invitations = Lists.newArrayList();
		
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PlayerPlayerInvitation.class);
        criteria.add(Restrictions.eq("playerId", playerId));
        criteria.addOrder(Order.desc("requestTime"));
        
        @SuppressWarnings("unchecked")
		List<PlayerPlayerInvitation> ppiLists = criteria.list();
		
        for(PlayerPlayerInvitation ppi: ppiLists){
        	InvitationResult invitationResult = new InvitationResult();
        	
        	PlayerBaseInfo fromPlayerBaseInfo = new PlayerBaseInfo();
        	PlayerBaseInfo toPlayerBaseInfo = new PlayerBaseInfo();

        	Player toPlayer = playerDao.findPlayer(ppi.getPlayerId());
        	CopyUtil.copy(toPlayerBaseInfo, toPlayer);
        	invitationResult.setToPlayer(toPlayerBaseInfo);
        	
        	Player fromPlayer = playerDao.findPlayer(ppi.getFromPlayerId());
        	CopyUtil.copy(fromPlayerBaseInfo, fromPlayer);
        	invitationResult.setFromPlayer(fromPlayerBaseInfo);
        	
        	invitationResult.setPlayerPlayerInvitation(ppi);
        	invitationResult.setMessageType(InvitationMessageType.RECEIVER);
        	invitations.add(invitationResult);
        }
		return invitations;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<InvitationResult> getAllTeamPlayerInvitationList(Long teamId) throws CopyException{
		List<InvitationResult> invitations = Lists.newArrayList();
		invitations.addAll(getSentTeamPlayerInvitationList(teamId));
		invitations.addAll(getReceivedTeamPlayerInvitationList(teamId));
		return invitations;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<InvitationResult> getSentTeamPlayerInvitationList(Long teamId) throws CopyException{
		List<InvitationResult> invitations = Lists.newArrayList();
		
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(TeamPlayerInvitation.class);
        criteria.add(Restrictions.eq("teamId", teamId));
        criteria.add(Restrictions.eq("type", InvitationType.TEAMTOPLAYER));
        criteria.addOrder(Order.desc("requestTime"));
        
        @SuppressWarnings("unchecked")
		List<TeamPlayerInvitation> tpInvitations = criteria.list();
		
        for(TeamPlayerInvitation tpi: tpInvitations){
        	InvitationResult invitationResult = new InvitationResult();
        	PlayerBaseInfo toPlayerBaseInfo = new PlayerBaseInfo();
        	TeamBaseInfo teamBaseInfo = new TeamBaseInfo();
        	
        	Player toPlayer = playerDao.findPlayer(tpi.getPlayerId());
            CopyUtil.copy(toPlayerBaseInfo, toPlayer);
            invitationResult.setToPlayer(toPlayerBaseInfo);
        	
            Team team = teamDao.findById(Team.class, tpi.getTeamId());
        	CopyUtil.copy(teamBaseInfo, team);
        	invitationResult.setTeam(teamBaseInfo);
        	
        	invitationResult.setTeamPlayerInvitation(tpi);
        	invitationResult.setMessageType(InvitationMessageType.SENDER);
        	invitations.add(invitationResult);
        }
		return invitations;
	}
	
	public List<InvitationResult> getReceivedTeamPlayerInvitationList(Long teamId) throws CopyException{
		List<InvitationResult> invitations = Lists.newArrayList();
		
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(TeamPlayerInvitation.class);
        criteria.add(Restrictions.eq("teamId", teamId));
        criteria.add(Restrictions.eq("type", InvitationType.PLAYERTOTEAM));
        criteria.addOrder(Order.desc("requestTime"));
        
        @SuppressWarnings("unchecked")
		List<TeamPlayerInvitation> tpInvitations = criteria.list();
		
        for(TeamPlayerInvitation tpi: tpInvitations){
        	InvitationResult invitationResult = new InvitationResult();
        	PlayerBaseInfo fromPlayerBaseInfo = new PlayerBaseInfo();
        	TeamBaseInfo teamBaseInfo = new TeamBaseInfo();
        	
        	Player toPlayer = playerDao.findPlayer(tpi.getPlayerId());
        	CopyUtil.copy(fromPlayerBaseInfo, toPlayer);
        	invitationResult.setToPlayer(fromPlayerBaseInfo);
        	
        	Team team = teamDao.findById(Team.class, tpi.getTeamId());
        	CopyUtil.copy(teamBaseInfo, team);
        	invitationResult.setTeam(teamBaseInfo);
        	
        	invitationResult.setTeamPlayerInvitation(tpi);
        	invitationResult.setMessageType(InvitationMessageType.RECEIVER);
        	invitations.add(invitationResult);
        }
		return invitations;
	}
	

}
