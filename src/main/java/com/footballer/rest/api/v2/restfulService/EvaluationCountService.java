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
import com.footballer.rest.api.v2.Response.EvaluationCountResponse;
import com.footballer.rest.api.v2.manager.EvaluationManagerV2;
import com.footballer.rest.api.v2.vo.PlayerMutualEvaluationAllCount;
import com.footballer.rest.api.v2.vo.PlayerMutualEvaluationMatchCount;
import com.footballer.util.ObjectUtil;

@Path("/mobile/v2/evaluationCount-service")
public class EvaluationCountService {
	Logger logger = LoggerFactory.getLogger(PlayerRecordService.class);
	
	@Autowired
	private EvaluationManagerV2 evaluationManagerV2;
	
	@GET
	@Path("/findMaxById/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public EvaluationCountResponse findMaxById(@PathParam("playerId") Long playerId) {

		EvaluationCountResponse response = new EvaluationCountResponse();

		if (ObjectUtil.isNull(playerId)) {
			logger.warn("player id can not be empty");
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("player id can not be empty");
			return response;
		}
		try{
			PlayerMutualEvaluationAllCount pmeac = evaluationManagerV2.findMaxProperty(playerId);
			response.setStatus(JsonResponse.SUCCESS);
			response.setEvaluationAllCount(pmeac);
		}catch(Exception e){
			logger.error("findMaxById error,playerId:"+playerId,e);
		
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
		return response;
	}
	
	@GET
	@Path("/findListById/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public EvaluationCountResponse findListByPlayerIdDesc(@PathParam("playerId") Long playerId) {

		EvaluationCountResponse response = new EvaluationCountResponse();

		if (ObjectUtil.isNull(playerId)) {
			logger.warn("player id can not be empty");
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("player id can not be empty");
			return response;
		}
		try{
			List<PlayerMutualEvaluationAllCount> eList = (List<PlayerMutualEvaluationAllCount>)evaluationManagerV2.findListByPlayerIdDesc(playerId);
			response.setStatus(JsonResponse.SUCCESS);
			response.setEvaluationAllCountList(eList);
		}catch(Exception e){
			logger.error("findListById error,playerId:"+playerId,e);
		
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
		return response;
	}
	
	@GET
	@Path("/findEvaluationMain/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public EvaluationCountResponse findEvaluationMain(@PathParam("playerId") Long playerId) {

		EvaluationCountResponse response = new EvaluationCountResponse();

		if (ObjectUtil.isNull(playerId)) {
			logger.warn("player id can not be empty");
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("player id can not be empty");
			return response;
		}
		try{
			PlayerMutualEvaluationAllCount pmeac = evaluationManagerV2.findMaxProperty(playerId);
			List<PlayerMutualEvaluationAllCount> eList = (List<PlayerMutualEvaluationAllCount>)evaluationManagerV2.findListByPlayerIdDesc(playerId);
			response.setStatus(JsonResponse.SUCCESS);
			response.setEvaluationAllCount(pmeac);
			response.setEvaluationAllCountList(eList);
			
			return response;
		}catch(Exception e){
			logger.error("findListByPlayerIdDesc error,playerId:"+playerId,e);
		
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
}
