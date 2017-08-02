package com.footballer.rest.api.v1.restfulService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.concurrent.action.NotificationMessage;
import com.footballer.notification.NotificationManager;
import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.dao.InvitationDao;
import com.footballer.rest.api.v1.dao.PlayerDao;
import com.footballer.rest.api.v1.dao.TeamDao;
import com.footballer.rest.api.v1.dao.UserDao;
import com.footballer.rest.api.v1.exception.CopyException;
import com.footballer.rest.api.v1.exception.DomainNotFoundException;
import com.footballer.rest.api.v1.request.TeamInvitePlayersRequest;
import com.footballer.rest.api.v1.response.InvitationResponse;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v1.result.InvitationResult;
import com.footballer.rest.api.v1.vo.Player;
import com.footballer.rest.api.v1.vo.PlayerPlayerInvitation;
import com.footballer.rest.api.v1.vo.Team;
import com.footballer.rest.api.v1.vo.TeamPlayer;
import com.footballer.rest.api.v1.vo.TeamPlayerInvitation;
import com.footballer.rest.api.v1.vo.enumType.InvitationType;
import com.footballer.util.CopyUtil;
import com.footballer.util.JacksonUtil;
import com.footballer.util.ObjectUtil;
import com.google.common.collect.Lists;

@Transactional
@Path("/mobile/v1/invitation-service")
public class InvitaionService {

	private static Logger logger = LoggerFactory.getLogger(InvitaionService.class);
	
	@Autowired
	public InvitationDao invitationDao;
	
	@Autowired
	public TeamDao teamDao;
	
	@Autowired
	public UserDao userDao;
	
    @Autowired
    public PlayerDao playerDao;
	
	@Autowired
	public NotificationManager notificationManager;
	
	//球员申请加入球队
	@POST
	@Path("/createPlayerToTeam/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse createPlayerToTeam(TeamPlayerInvitation tpInvitation) throws DomainNotFoundException {
		JsonResponse response = new JsonResponse();
		if (!ObjectUtil.isNotNull(tpInvitation.getTeamId()) && !ObjectUtil.isNotNull(tpInvitation.getPlayerId())) {
			logger.warn("team / player id can not be empty");
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("team / player id can not be empty");
			return response;
		}
		tpInvitation.setType(InvitationType.PLAYERTOTEAM);
		try{
			if(invitationDao.checkTeamPlayerInvitationisNotExist(tpInvitation)){
				invitationDao.createPlayerToTeam(tpInvitation);
				
				Team team = teamDao.findById(Team.class, tpInvitation.getTeamId());
		    	Player player = playerDao.findPlayer(tpInvitation.getPlayerId());
		    	String content = player.getNickName() +" 申请加入您的球队："+ team.getName();
				Map<String, Object> message = NotificationMessage.getApplyTeamInvitation(
						tpInvitation.getTeamId(), tpInvitation.getPlayerId(),
						content);
		    	notificationManager.sendSingleMessage(message, team.getCaptainUserId());
		    	logger.debug(message+" 推送结束 ...");
				
				response.setStatus(JsonResponse.SUCCESS);
				response.setMessage("球员: "+player.getNickName()+" 申请加入球队:"+ team.getName() +"成功");
			}else{
				response.setStatus(JsonResponse.ERROR);
				response.setMessage("已存在当前邀请");
			}
		}catch(Exception e){
			logger.error("createPlayerToTeam error,param:"+JacksonUtil.toJson(tpInvitation),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	//队长同意球员申请加入球队的请求
	@POST
	@Path("/approvePlayerToTeam/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse approvePlayerToTeam(TeamPlayerInvitation tpInvitation) throws DomainNotFoundException {

		InvitationResponse<TeamPlayerInvitation> response = new InvitationResponse<>();
		try{
			if(invitationDao.approveMentTeamPlayer(tpInvitation, InvitationType.PLAYERTOTEAM, true)){
				TeamPlayer tp = teamDao.addPlayerToTeam(tpInvitation.getTeamId(),tpInvitation.getPlayerId());
				
				Team team = teamDao.findById(Team.class, tpInvitation.getTeamId());
				Player captain = playerDao.findPlayer(team.getCaptainUserId());
		    	String content = "球队："+ team.getName() +" 队长： "+captain.getNickName() +" 同意您加入球队";
		    	Map<String, Object> message = NotificationMessage
						.getApplyTeamInvitationCompleted(
								NotificationMessage.APPROVED,
								tpInvitation.getTeamId(),
								tpInvitation.getPlayerId(), content);
		    	notificationManager.sendSingleMessage(message, tpInvitation.getPlayerId());
		    	logger.debug(message+ " 推送结束 ...");
		    	if(tp == null){ //只在首次加入球队时创建资金账户
		    		userDao.createPlayerDepositAccount(tpInvitation.getPlayerId(), tpInvitation.getTeamId());
		    	}
		    	
				
				response.setDomain(tpInvitation);
				response.setStatus(JsonResponse.SUCCESS);
				response.setMessage("加入球队成功");
			}else{
				response.setMessage("不存在这条球员申请加入球队的记录");
				response.setStatus(JsonResponse.ERROR);
			}
		}catch(Exception e){
			logger.error("approvePlayerToTeam error,param:"+JacksonUtil.toJson(tpInvitation),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	//队长  拒绝 球员申请加入球队的请求
	@POST
	@Path("/refusePlayerToTeam/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse refusePlayerToTeam(TeamPlayerInvitation tpInvitation) throws DomainNotFoundException {

		InvitationResponse<TeamPlayerInvitation> response = new InvitationResponse<>();
		try{
			if(invitationDao.approveMentTeamPlayer(tpInvitation, InvitationType.PLAYERTOTEAM, false)){
				
				Team team = teamDao.findById(Team.class, tpInvitation.getTeamId());
				Player captain = playerDao.findPlayer(team.getCaptainUserId());
		    	String content = "球队："+ team.getName() +" 队长： "+captain.getNickName() +" 拒绝了您加入球队的请求";
				Map<String, Object> message = NotificationMessage
						.getApplyTeamInvitationCompleted(NotificationMessage.REJECTED,
								tpInvitation.getTeamId(),
								tpInvitation.getPlayerId(), content); 
		    	notificationManager.sendSingleMessage(message, tpInvitation.getPlayerId());
		    	logger.debug(message+ " 推送结束 ...");
		    	
				response.setDomain(tpInvitation);
				response.setStatus(JsonResponse.SUCCESS);
				response.setMessage("球队拒绝您的加入");
			}else{
				response.setStatus(JsonResponse.ERROR);
			}
		}catch(Exception e){
			logger.error("refusePlayerToTeam error,param:"+JacksonUtil.toJson(tpInvitation),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	//球队邀请球员加入
	@POST
	@Path("/teamInvitePlayers/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse teamInvitePlayers(TeamInvitePlayersRequest request) {
		JsonResponse response = new JsonResponse();
		if (!ObjectUtil.isNotNull(request.getTeamId()) && !ObjectUtil.isNotNull(request.getPlayerIds())) {
			logger.warn("team / player id can not be empty");
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("team / player id can not be empty");
			return response;
		}
		List<Long> toPlayerIdList = Lists.newArrayList();
		try {
			toPlayerIdList = CopyUtil.listDeepCopy(request.getPlayerIds());
		} catch (ClassNotFoundException | IOException e) {
			logger.error("copy playerId error",e);
		}
		
		try{
			for(Long toplayerId: request.getPlayerIds()){
				TeamPlayerInvitation tpInvitation =new TeamPlayerInvitation();
				tpInvitation.setPlayerId(toplayerId);
				tpInvitation.setTeamId(request.getTeamId());
				tpInvitation.setType(InvitationType.TEAMTOPLAYER);
				if(!invitationDao.checkTeamPlayerInvitationisNotExist(tpInvitation)){
					toPlayerIdList.remove(toplayerId);//移除已经存在的邀请
				}
			}
			if(toPlayerIdList.size()>0){
				invitationDao.TeamInvitePlayers(request.getTeamId(), toPlayerIdList);
				
				Team team = teamDao.findById(Team.class, request.getTeamId());
				String message = "球队 "+ team.getName()+" 邀请您加入";
				notificationManager.sendMessage(message, request.getPlayerIds());
				
				response.setStatus(JsonResponse.SUCCESS);
				response.setMessage("创建球队邀请球员成功，并已移除已经存在的相同邀请");
			}else{
				response.setStatus(JsonResponse.ERROR);
				response.setMessage("当前邀请信息已经存在");
			}
		}catch(Exception e){
			logger.error("teamInvitePlayers error,param:"+JacksonUtil.toJson(request),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
		}
		
		return response;
	}
	
	//球员 同意 球队的邀请
	@POST
	@Path("/approveTeamToPlayer/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse approveTeamToPlayer(TeamPlayerInvitation tpInvitation) throws DomainNotFoundException {

		InvitationResponse<TeamPlayerInvitation> response = new InvitationResponse<>();
		try{
			if(invitationDao.approveMentTeamPlayer(tpInvitation, InvitationType.TEAMTOPLAYER, true)){
				teamDao.addPlayerToTeam(tpInvitation.getTeamId(),tpInvitation.getPlayerId());
				
				Team team = teamDao.findById(Team.class, tpInvitation.getTeamId());
				Player player = playerDao.findPlayer(tpInvitation.getPlayerId());
		    	String content = "球员："+ player.getNickName() +" 同意了您邀请他加入您的球队 "+team.getName()+" 的请求";
		    	Map<String, Object> message = NotificationMessage
						.getJoinTeamInvitationCompleted(NotificationMessage.APPROVED,
								tpInvitation.getTeamId(),
								tpInvitation.getPlayerId(), content);
		    	notificationManager.sendSingleMessage(message, team.getCaptainUserId());
		    	logger.debug(message+ " 推送结束 ...");
		    	
		    	userDao.createPlayerDepositAccount(tpInvitation.getPlayerId(), tpInvitation.getTeamId());
				
				response.setDomain(tpInvitation);
				response.setStatus(JsonResponse.SUCCESS);
				response.setMessage("接受加入球队");
			}else{
				response.setStatus(JsonResponse.ERROR);
			}
		}catch(Exception e){
			logger.error("approveTeamToPlayer error,param:"+JacksonUtil.toJson(tpInvitation),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	//球员 拒绝 球队的邀请
	@POST
	@Path("/refuseTeamToPlayer/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse refuseTeamToPlayer(TeamPlayerInvitation tpInvitation) throws DomainNotFoundException {

		InvitationResponse<TeamPlayerInvitation> response = new InvitationResponse<>();
		try{
			if(invitationDao.approveMentTeamPlayer(tpInvitation, InvitationType.TEAMTOPLAYER, false)){
				
				Team team = teamDao.findById(Team.class, tpInvitation.getTeamId());
				Player player = playerDao.findPlayer(tpInvitation.getPlayerId());
		    	String content = "球员："+ player.getNickName() +" 拒绝了您邀请他加入您的球队 "+team.getName()+" 的请求";
				Map<String, Object> message = NotificationMessage
						.getJoinTeamInvitationCompleted(NotificationMessage.REJECTED,
								tpInvitation.getTeamId(),
								tpInvitation.getPlayerId(), content);
		    	notificationManager.sendSingleMessage(message, team.getCaptainUserId());
		    	logger.debug(message+ " 推送结束 ...");
				
				response.setDomain(tpInvitation);
				response.setStatus(JsonResponse.SUCCESS);
				response.setMessage("拒绝加入球队");
			}else{
				response.setStatus(JsonResponse.ERROR);
			}
		}catch(Exception e){
			logger.error("refuseTeamToPlayer error,param:"+JacksonUtil.toJson(tpInvitation),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@POST
	@Path("/updateTeamPlayerInvitation/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse updateTeamPlayerInvitation(TeamPlayerInvitation tpInvitation) {

		InvitationResponse<TeamPlayerInvitation> response = new InvitationResponse<>();
		try{
			invitationDao.updateTeamPlayerInvitation(tpInvitation);
			response.setDomain(tpInvitation);
			response.setStatus(JsonResponse.SUCCESS);
			response.setMessage("save team success");
		}catch(Exception e){
			logger.error("refuseTeamToPlayer error,param:"+JacksonUtil.toJson(tpInvitation),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	//球员邀请球员成为好友
	@POST
	@Path("/playerInvitePlayer/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse playerInvitePlayer(PlayerPlayerInvitation ppi) throws DomainNotFoundException {
		JsonResponse response = new JsonResponse();
		if (ObjectUtil.isNull(ppi.getFromPlayerId()) && ObjectUtil.isNull(ppi.getPlayerId()) 
				|| ppi.getFromPlayerId() == 0 || ppi.getPlayerId() == 0) {
			logger.warn("邀请人 / 被邀请人  id 不能为空 或者 0");
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("邀请人 / 被邀请人  id 不能为空 或者 0");
			return response;
		}
		try{
			if(ObjectUtil.isNull(invitationDao.findPlayerTOPlayer(ppi.getFromPlayerId(), ppi.getPlayerId()))){
				invitationDao.PlayerToPlayer(ppi);
				
		    	Player fromPlayer = playerDao.findPlayer(ppi.getFromPlayerId());
		    	String content = fromPlayer.getNickName() +" 邀请您成为他的球友";
				Map<String, Object> message = NotificationMessage
						.getFriendInvitationMessage(ppi.getFromPlayerId(),
								ppi.getPlayerId(), content);
		    	notificationManager.sendSingleMessage(message, ppi.getPlayerId());
		    	logger.debug(message + " 推送结束 ...");
				
				response.setStatus(JsonResponse.SUCCESS);
				response.setMessage("创建加好友请求成功");
			}else{
				response.setStatus(JsonResponse.ERROR);
				response.setMessage("已存在当前邀请");
			}
		}catch(Exception e){
			logger.error("playerInvitePlayer error,param:"+JacksonUtil.toJson(ppi),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	//同意加好友请求
	@POST
	@Path("/approvePlayerToPlayer/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse approvePlayerToPlayer(PlayerPlayerInvitation ppi) throws DomainNotFoundException {

		InvitationResponse<PlayerPlayerInvitation> response = new InvitationResponse<>();
		try{
			if(invitationDao.approveMentPlayer2Player(ppi, true)){
				playerDao.addPlayerAsFriend(ppi.getFromPlayerId(), ppi.getPlayerId());
				
				Player player = playerDao.findPlayer(ppi.getPlayerId());
				String message = "球员 "+ player.getNickName() +" 同意了您的加好友请求";
				Map<String, Object> map = NotificationMessage
						.getFriendInvitationCompleted(NotificationMessage.APPROVED,
								ppi.getFromPlayerId(), ppi.getPlayerId(), message);
		    	notificationManager.sendSingleMessage(map, ppi.getFromPlayerId());
		    	logger.debug(message+ " 推送结束 ...");
				
				response.setDomain(ppi);
				response.setStatus(JsonResponse.SUCCESS);
				response.setMessage("接受加为好友成功");
			}else{
				response.setStatus(JsonResponse.ERROR);
			}
		}catch(Exception e){
			logger.error("approvePlayerToPlayer error,param:"+JacksonUtil.toJson(ppi),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	//拒绝加好友请求
	@POST
	@Path("/refusePlayerToPlayer/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse refusePlayerToPlayer(PlayerPlayerInvitation ppi) throws DomainNotFoundException {

		InvitationResponse<PlayerPlayerInvitation> response = new InvitationResponse<>();
		try{
			if(invitationDao.approveMentPlayer2Player(ppi, false)){
				
				Player player = playerDao.findPlayer(ppi.getPlayerId());
				String content = "球员 "+ player.getNickName() +" 拒绝了您的加好友请求";
				Map<String, Object> message = NotificationMessage
						.getFriendInvitationCompleted(NotificationMessage.REJECTED,
								ppi.getFromPlayerId(), ppi.getPlayerId(), content);
		    	notificationManager.sendSingleMessage(message, ppi.getFromPlayerId());
		    	logger.debug(message+ " 推送结束 ...");
		    	
				response.setDomain(ppi);
				response.setStatus(JsonResponse.SUCCESS);
				response.setMessage("拒绝加为好友");
			}else{
				response.setStatus(JsonResponse.ERROR);
			}
		}catch(Exception e){
			logger.error("refusePlayerToPlayer error,param:"+JacksonUtil.toJson(ppi),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
		}
		return response;
	}
	
	//获取球友的所有 好友请求和邀请
	@GET
	@Path("/getAllPlayerFriendsInvitationList/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse getAllPlayerFriendsInvitationList(@PathParam("playerId") Long playerId) {
		InvitationResponse<?> response = new InvitationResponse<>();
		
		if (null == playerId) {
			logger.warn("playerId id can not be empty");
            response.setStatus(JsonResponse.ERROR);
            response.setMessage("playerId id can not be empty");
            return response;
        }
		
		try {
			List<InvitationResult> invitations = invitationDao.getAllPlayerFriendsInvitationList(playerId);
			response.setInvitations(invitations);
		} catch (CopyException e) {
			logger.error("getAllPlayerFriendsInvitationList error,param:playerId:"+playerId,e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("获取球员好友请求失败");
		}
		response.setStatus(JsonResponse.SUCCESS);
		response.setMessage("获取球员好友请求成功");
		return response;
	}
	
	
		//获取球队的所有 新球员请求和邀请
		@GET
		@Path("/getAllTeamPlayerInvitationList/{playerId}/{identity}/{appId}/{apptoken}")
		@Produces(MediaType.APPLICATION_JSON)
		public JsonResponse getAllTeamPlayerInvitationList(@PathParam("teamId") Long teamId) {
			InvitationResponse<?> response = new InvitationResponse<>();
			
			if (null == teamId) {
				logger.warn("teamId  can not be empty");
	            response.setStatus(JsonResponse.ERROR);
	            response.setMessage("teamId  can not be empty");
	            return response;
	        }
			
			try {
				List<InvitationResult> invitations = invitationDao.getAllPlayerFriendsInvitationList(teamId);
				response.setInvitations(invitations);
			} catch (CopyException e) {
				logger.warn("getAllTeamPlayerInvitationList error,param:teamId:"+teamId,e);
				response.setStatus(JsonResponse.ERROR);
				response.setMessage("获取球队的所有新球员请求和邀请失败");
			}
			response.setStatus(JsonResponse.SUCCESS);
			response.setMessage("获取球队的所有新球员请求和邀请成功");
			return response;
		}
		
		//消息中心 球员所有发出和收到的邀请
		@GET
        @Path( "/getAllInvitationList/{playerId}/{identity}/{appId}/{apptoken}" )
        @Produces(MediaType. APPLICATION_JSON )
        public JsonResponse getAllInvitationList(@PathParam ("playerId" ) Long playerId ) 
		{
                InvitationResponse<?> response = new InvitationResponse<>();
                 if ( null == playerId )
                 {
                	 response.setStatus(JsonResponse. ERROR );
                	 response.setMessage( "playerId id can not be empty" );
                	 return response ;
                 }
                 try {
                          List<InvitationResult> invitations = invitationDao .getAllInvitationList( playerId);
                           response.setInvitations( invitations );
                } catch (CopyException e ) {
                	logger.warn("getAllInvitationList error,playerId:"+playerId,e);
                           response.setStatus(JsonResponse. ERROR );
                           response.setMessage( "获取球员消息列表失败" );
                }
                 response.setStatus(JsonResponse. SUCCESS );
                 response.setMessage( "获取球员消息列表成功" );
                 return response ;
       }
		
}
