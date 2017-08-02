package com.footballer.rest.api.v2.restfulService;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v2.Response.DashboardResponse;
import com.footballer.rest.api.v2.manager.EvaluationManagerV2;
import com.footballer.rest.api.v2.vo.Player;
import com.footballer.util.ObjectUtil;

@Path("/mobile/v2/dashboard-service")
public class DashboardService {
	 private static Logger logger = LoggerFactory.getLogger(PlayerRecordService.class);
	@Autowired
	private EvaluationManagerV2 evaluationManagerV2;
	
	@GET
	@Path("/findRecognisedPlayers/{eventId}/{playerId}/{playerSkill}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public DashboardResponse findMaxById(@PathParam("playerId") Long playerId,@PathParam("eventId") Long eventId,@PathParam("playerSkill") String playerSkill) {

		DashboardResponse response = new DashboardResponse();

		if (ObjectUtil.isNull(playerId)) {
			logger.warn("playerId id can not be empty");
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("playerId id can not be empty");
			return response;
		}
		try{
			List<Player> recognisedPlayers = evaluationManagerV2.findRecognisedPlayersByEventIdAndPlayerIdAndProperty(eventId, playerId, playerSkill);
			response.setStatus(JsonResponse.SUCCESS);
			response.setRecogniseList(recognisedPlayers);
			
			return response;
		}catch(Exception e){
			logger.error("findRecognisedPlayers error",e);
			logger.error("findRecognisedPlayers error,playerId:{};eventId:{};playerSkill:{}",playerId,eventId,playerSkill);
		
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@GET
	@Path("/findDashboard/{eventId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public DashboardResponse findDashboard(@PathParam("eventId") Long eventId) {

		DashboardResponse response = new DashboardResponse();
		if (ObjectUtil.isNull(eventId)) {
			logger.warn("event id can not be empty");
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("event id can not be empty");
			return response;
		}
		try{
			response = evaluationManagerV2.findDashboard(eventId);
			response.setStatus(JsonResponse.SUCCESS);
			
			return response;
		}catch(Exception e){
			logger.error("findRecognisedPlayers error,param:eventId:"+eventId,e);
		
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
	}
}
