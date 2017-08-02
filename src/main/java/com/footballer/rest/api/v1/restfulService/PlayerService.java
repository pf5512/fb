package com.footballer.rest.api.v1.restfulService;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import jersey.repackaged.com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.notification.NotificationManager;
import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.dao.EventDao;
import com.footballer.rest.api.v1.dao.PlayerDao;
import com.footballer.rest.api.v1.domain.FootballPlayerTeamActivity;
import com.footballer.rest.api.v1.domain.PlayerSkillPropertyRanking;
import com.footballer.rest.api.v1.domain.PlayerSkillPropertyTeamRanking;
import com.footballer.rest.api.v1.exception.CopyException;
import com.footballer.rest.api.v1.manager.PlayerManager;
import com.footballer.rest.api.v1.request.ConfirmEventRequest;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v1.response.PlayerSkillPropertyRankingResponse;
import com.footballer.rest.api.v1.response.PlayerWithTeamBaseInfoResponse;
import com.footballer.rest.api.v1.result.PlayerWithTeamBaseInfoResult;
import com.footballer.rest.api.v1.vo.Event;
import com.footballer.util.JacksonUtil;


import jersey.repackaged.com.google.common.collect.Lists;

@Path("/mobile/v1/player-service")
public class PlayerService {

	private static Logger logger = LoggerFactory.getLogger(PlayerService.class);
	
    @Autowired
	private EventDao eventDao;
    
    @Autowired
    public PlayerDao playerDao;
    
    @Autowired
    public PlayerManager playerManager;
    
    @Autowired
	public NotificationManager notificationManager;
    
	@GET
	@Path("/getPlayerFriendsList/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse getPlayerFriendsList(
			@PathParam("playerId") Long playerId) throws CopyException {

		PlayerWithTeamBaseInfoResponse response = new PlayerWithTeamBaseInfoResponse();
		List<PlayerWithTeamBaseInfoResult> playerWithTeamBaseInfoList = null;
		try{
			playerWithTeamBaseInfoList = playerDao.getPlayerFriendsList(playerId);
		}catch(Exception e){
			logger.error("getPlayerFriendsList error,param:playerId:"+playerId,e);
		}
		response.setPlayerWithTeamBaseInfoList(playerWithTeamBaseInfoList);
		response.setStatus(JsonResponse.SUCCESS);
		response.setMessage("获取当前球员的球友列表成功");
		return response;
	}
	
	@GET
	@Path("/getPlayerPerprotyRank/{playerSkillType}/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse getPlayerPerprotyRank(
			@PathParam("playerSkillType") String playerSkillType,
			@PathParam("playerId") Long playerId) throws CopyException {

		PlayerSkillPropertyRankingResponse response = new PlayerSkillPropertyRankingResponse();
		try{
			List<PlayerSkillPropertyRanking> friendsRankingList = playerDao.getPlayerFriendsRankingOfSkillType(playerSkillType, playerId);
			List<PlayerSkillPropertyTeamRanking> teamMembersRankingList = playerDao.getPlayerTeamsRankingOfSkillType(playerSkillType, playerId);
			response.setPlayerSkillType(playerSkillType);
			response.setFriendsRankingList(friendsRankingList);
			response.setTeamMembersRankingList(teamMembersRankingList);
		}catch(Exception e){
			logger.error("getPlayerPerprotyRank error",e);
			logger.error("getPlayerPerprotyRank error,param:playerId:{};playerSkillType:{}",playerId,playerSkillType);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}

		response.setStatus(JsonResponse.SUCCESS);
		response.setMessage("获取当前球员的球友列表成功");
		return response;
	}
	
    @POST
    @Path("/confirmEvent/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
	public JsonResponse confirmEvent(ConfirmEventRequest request) {
		JsonResponse response = new JsonResponse();
		try{
			playerManager.processEventConfirmation(request.getEventId(),request.getBalances(), request.getActivities());
			Event e = eventDao.findById(Event.class, request.getEventId());
			String message = e.toMessage()+ ", 队长已完成出席扣款确认,请查看赛果和互评情况";
			List<FootballPlayerTeamActivity> playerList= request.getActivities();
			List<Long> playerIds = Lists.newArrayList();
			for(FootballPlayerTeamActivity fpta: playerList){
				playerIds.add(fpta.getPlayerId());
			}
	    	notificationManager.sendMessage(message, playerIds);
		}catch(Exception e){
			logger.error("confirmEvent error,param:"+JacksonUtil.toJson(request),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
		response.setStatus(JsonResponse.SUCCESS);
		response.setMessage("确认event成功");
		return response;
	}

}