package com.footballer.rest.api.v2.restfulService;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.notification.NotificationManager;
import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.dao.EventDao;
import com.footballer.rest.api.v1.dao.PlayerDao;
import com.footballer.rest.api.v1.domain.FootballPlayerTeamActivity;
import com.footballer.rest.api.v1.exception.DomainNotFoundException;
import com.footballer.rest.api.v1.request.ConfirmEventRequest;
import com.footballer.rest.api.v1.request.FootballGamePlayerCommentRequest;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v1.vo.Event;
import com.footballer.rest.api.v1.vo.Player;
import com.footballer.rest.api.v2.Response.EventCommentsResponse;
import com.footballer.rest.api.v2.manager.CommonManager;
import com.footballer.rest.api.v2.manager.EventCommentsManager;
import com.footballer.util.JacksonUtil;
import com.footballer.util.ObjectUtil;

import jersey.repackaged.com.google.common.collect.Lists;


@Path("/mobile/v2/event-service")
public class EventService {
	
	private static Logger logger = LoggerFactory.getLogger(EventService.class);
	
	@Autowired
	private EventCommentsManager ecManager;
	
	@Autowired
	private CommonManager cMgr;
	
	@Autowired
	public NotificationManager notificationManager;
	
	@Autowired
	public PlayerDao playerDao;
	
	@Autowired
	public EventDao eventDao;
	
    //Dashboard最终状态时球员发表comments
    @POST
    @Path("/sendComments/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse sendComments(FootballGamePlayerCommentRequest request) throws DomainNotFoundException {
    	JsonResponse response = new JsonResponse(); 
    	Long eventId = request.getEventId();
    	Long playerId = request.getPlayerId();
    	String comments = request.getComments();
    	try{
	    	if (ObjectUtil.isNull(request)|| ObjectUtil.isNull(eventId)|| ObjectUtil.isNull(playerId)|| ObjectUtil.isNull(comments)
	    			|| !cMgr.checkEventAndPlayerExist(eventId, playerId)) {
	    		logger.warn("eventId/playerId/comments can not be null or event/player not exist");
	    		response.setMessage("eventId/playerId/comments can not be null or event/player not exist");
	    		response.setStatus(JsonResponse.ERROR);
			}else{
				ecManager.createEventComments(eventId, playerId, comments);
				Event e = eventDao.findById(Event.class, eventId);
				List<Long> playerIds = eventDao.findPlayerIdByEventId(eventId);
				Player player = playerDao.findById(Player.class, request.getPlayerId());
				String message = e.getStartDateDisplay()+" 赛事留言 - "+player.getNickName() + " : "+ comments;
				notificationManager.sendMessage(message, playerIds);
				response.setMessage("留言发送成功");
		        response.setStatus(JsonResponse.SUCCESS);
			}
	    	return response;
	    }catch(Exception e){
			logger.error("sendComments error,param:"+JacksonUtil.toJson(request),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
    }
    
    //获取赛事最终状态 所有球员的留言 按时间最新排序
    @GET
    @Path("/getEventComments/{eventId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public EventCommentsResponse getEventComments(@PathParam("eventId") Long eventId) throws DomainNotFoundException {
    	EventCommentsResponse response = new EventCommentsResponse(); 
    	try{
	    	if(ObjectUtil.isNull(eventId)){
	    		logger.warn("eventId can not null");
	    		response.setMessage("eventId can not null");
	    		response.setStatus(JsonResponse.ERROR);
	    	}else{
	    		if(cMgr.checkEventExist(eventId)){
	    			response.setEcList(ecManager.getEventCommentsList(eventId));
	    			response.setStatus(JsonResponse.SUCCESS);
	    		}
	    	}
	    	return response;
    	}catch(Exception e){
			logger.error("getEventComments error,param:eventId:"+eventId,e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
    }
    
    
//    //娱乐赛事 赛后 队长确认 出席和扣款情况
//    @POST
//    @Path("/confirmEvent/{identity}/{appId}/{apptoken}")
//    @Produces(MediaType.APPLICATION_JSON)
//	public JsonResponse confirmEvent(ConfirmEventRequest request) {
//		JsonResponse response = new JsonResponse();
//		try{
//			playerManager.processEventConfirmation(request.getEventId(),request.getBalances(), request.getActivities());
//			Event e = eventDao.findById(Event.class, request.getEventId());
//			String message = e+ ", 队长已完成出席扣款确认,请查看赛果和互评情况";
//			List<FootballPlayerTeamActivity> playerList= request.getActivities();
//			List<Long> playerIds = Lists.newArrayList();
//			for(FootballPlayerTeamActivity fpta: playerList){
//				playerIds.add(fpta.getPlayerId());
//			}
//	    	notificationManager.sendMessage(message, playerIds);
//		}catch(Exception e){
//			logger.error("confirmEvent error,param:"+JacksonUtil.toJson(request),e);
//			response.setStatus(JsonResponse.ERROR);
//			response.setMessage(e.getMessage());
//			return response;
//		}
//		response.setStatus(JsonResponse.SUCCESS);
//		response.setMessage("确认event成功");
//		return response;
//	}
}
