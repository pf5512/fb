package com.footballer.rest.api.v2.restfulService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v2.Response.PlayerRecordResponse;
import com.footballer.rest.api.v2.manager.PlayerRecordManager;
import com.footballer.util.ObjectUtil;

@Path("/mobile/v2/playerRecord-service")
public class PlayerRecordService {
	
	private static Logger logger = LoggerFactory.getLogger(PlayerRecordService.class);
	
	@Autowired
	private PlayerRecordManager playerRecordManager;
	
	@GET
	@Path("/findById/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public PlayerRecordResponse findPlayerRecordById(@PathParam("playerId") Long playerId) {

		PlayerRecordResponse response = new PlayerRecordResponse();

		if (ObjectUtil.isNull(playerId)) {
			logger.warn("player id can not be empty");
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("player id can not be empty");
			return response;
		}
		try{
			response = playerRecordManager.getPlayerRecordMain(playerId);
			
			response.setStatus(JsonResponse.SUCCESS);
			
			return response;
		}catch(Exception e){
			logger.error("findById error,param:playerId:"+playerId,e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
/*	@GET
	@Path("/findTotalById/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public PlayerRecordResponse findPlayerRecordTotalById(@PathParam("playerId") Long playerId) {

		PlayerRecordResponse response = new PlayerRecordResponse();

		if (ObjectUtil.isNull(playerId)) {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("Arena id can not be empty");
			return response;
		}
		
		List<PlayerRecord> prTotals = playerRecordManager.findPlayerRecordByTotal(playerId);
		
		response.setStatus(JsonResponse.SUCCESS);
		response.setPlayerRecordTotals(prTotals);
		
		return response;
	}
	
	@GET
	@Path("/findYearById/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public PlayerRecordResponse findPlayerRecordYearById(@PathParam("playerId") Long playerId) {

		PlayerRecordResponse response = new PlayerRecordResponse();

		if (ObjectUtil.isNull(playerId)) {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("Arena id can not be empty");
			return response;
		}
		
		List<PlayerRecord> prYears = playerRecordManager.findPlayerRecordByYear(playerId);
		
		response.setStatus(JsonResponse.SUCCESS);
		response.setPlayerRecordYears(prYears);
		
		return response;
	}*/
}
