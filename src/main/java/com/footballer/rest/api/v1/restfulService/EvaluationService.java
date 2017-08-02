package com.footballer.rest.api.v1.restfulService;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.filter.AppContextHolder;
import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.dao.EvaluationDao;
import com.footballer.rest.api.v1.dao.EventDao;
import com.footballer.rest.api.v1.dao.PlayerDao;
import com.footballer.rest.api.v1.domain.PlayerBaseInfo;
import com.footballer.rest.api.v1.domain.PlayerRecognisedRecord;
import com.footballer.rest.api.v1.domain.RecogniseOtherPlayerRecord;
import com.footballer.rest.api.v1.exception.CopyException;
import com.footballer.rest.api.v1.response.EvaluationResponse;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v1.vo.Account;
import com.footballer.rest.api.v1.vo.Event;
import com.footballer.rest.api.v1.vo.PlayerMutualEvaluation;
import com.footballer.rest.api.v2.manager.StatisticsManager;
import com.footballer.util.JacksonUtil;
import com.footballer.util.ObjectUtil;

@Path("/mobile/v1/evaluation-service")
public class EvaluationService {

	private static Logger logger = LoggerFactory.getLogger(EvaluationService.class);

	@Autowired
	public EventDao eventDao;
	
	@Autowired
	public PlayerDao playerDao;

	@Autowired
	public EvaluationDao evaluationDao;
	
	@Autowired
	public StatisticsManager statisticsManager;

	
	//获取当前球员的的赛后认同记录和用户自己当前认同点使用情况
	@GET
	@Path("/getPlayerEvaluation/{eventId}/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse getPlayerEvaluation(@PathParam("eventId") Long eventId,
																				  @PathParam("playerId") Long playerId) throws CopyException {

		EvaluationResponse response = new EvaluationResponse();
		Account account = AppContextHolder.getContext().getAccount();
		try{
			Event event = eventDao.findById(Event.class, eventId);
			List<PlayerBaseInfo> joinedPlayerList = playerDao.getJoinedEventPlayerList(eventId);
			List<PlayerRecognisedRecord> currentPlayerRecognisedRecords= evaluationDao.getPlayerRecognisedRecords(eventId, playerId);
			List<RecogniseOtherPlayerRecord> recogniseOtherPlayerRecords= evaluationDao.getRecogniseOtherPlayerRecords(eventId, account.getId());
			
			response.setEvent(event);
			response.setJoinedPlayerList(joinedPlayerList);
			response.setCurrentPlayerRecognisedRecords(currentPlayerRecognisedRecords);
			response.setRecogniseOtherPlayerRecords(recogniseOtherPlayerRecords);
			response.setStatus(JsonResponse.SUCCESS);
			response.setMessage("获取当前球员认可记录成功");
			return response;
		}catch(Exception e){
			logger.error("getPlayerEvaluation error",e);
			logger.error("getPlayerEvaluation error,param:eventId:{};playerId:{}",eventId,playerId);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
	}

	//认同球员技术能力
	@POST
	@Path("/recognisePlayer/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse recognisePlayer(PlayerMutualEvaluation pme) throws Exception {
		JsonResponse response = new JsonResponse();
		
		if (ObjectUtil.isNull(pme.getEventId()) || 
			  ObjectUtil.isNull(pme.getPlayerId()) ||
			  ObjectUtil.isNull(pme.getPlayerId()) ||
			  ObjectUtil.isNull(pme.getPlayerId())    ) 
		{
			logger.warn("event_id / player_id /recognise_player_id can not be empty");
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("event_id / player_id /recognise_player_id can not be empty");
			return response;
		}
		try{
			if(evaluationDao.savePlayerMutualEvaluation(pme)){
				response.setStatus(JsonResponse.SUCCESS);
				response.setMessage("认可球员成功");
			}else{
				response.setStatus(JsonResponse.ERROR);
				response.setMessage("认可球员失败");
			}
		}catch(Exception e){
			logger.error("recognisePlayer error,param:"+JacksonUtil.toJson(pme),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
		return response;
	}

	//获取当前球员的所有认同和被认同记录
	/**
	 * 1.所有被认同技能及被哪些人认同
	 * 2.认同次数/人次排名
	 * 3.所有认同他人的记录
	 */
	@GET
	@Path("/getPlayerAllEvaluationInfo/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse getPlayerAllEvaluationInfo(@PathParam("playerId") Long playerId) throws CopyException {

		EvaluationResponse response = new EvaluationResponse();
		try{
			List<PlayerRecognisedRecord> currentPlayerRecognisedRecords = evaluationDao.getPlayerAllRecognisedRecords(playerId);
			List<RecogniseOtherPlayerRecord> recogniseOtherPlayerRecords = evaluationDao.getAllRecogniseOtherPlayerRecords(playerId);
	
			response.setCurrentPlayerRecognisedRecords(currentPlayerRecognisedRecords);
			response.setRecogniseOtherPlayerRecords(recogniseOtherPlayerRecords);
			response.setStatus(JsonResponse.SUCCESS);
			response.setMessage("获取当前球员认可记录成功");
			return response;
		}catch(Exception e){
			logger.error("getPlayerAllEvaluationInfo error,param:playerId:"+playerId,e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	
	
}