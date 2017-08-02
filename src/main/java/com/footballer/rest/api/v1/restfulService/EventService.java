package com.footballer.rest.api.v1.restfulService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.concurrent.action.NotificationMessage;
import com.footballer.filter.AppContextHolder;
import com.footballer.notification.NotificationManager;
import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.dao.EventDao;
import com.footballer.rest.api.v1.domain.PlayerWithStatus;
import com.footballer.rest.api.v1.exception.DomainNotFoundException;
import com.footballer.rest.api.v1.manager.EventManager;
import com.footballer.rest.api.v1.manager.UserManager;
import com.footballer.rest.api.v1.request.CreateEventRequest;
import com.footballer.rest.api.v1.request.PlayerCheckInRequest;
import com.footballer.rest.api.v1.response.EventResponse;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v1.response.SignResponse;
import com.footballer.rest.api.v1.result.EventResult;
import com.footballer.rest.api.v1.vo.Account;
import com.footballer.rest.api.v1.vo.Event;
import com.footballer.rest.api.v1.vo.EventInvitation;
import com.footballer.rest.api.v1.vo.enumType.EventStatus;
import com.footballer.rest.api.v1.vo.enumType.EventType;
import com.footballer.rest.api.v1.vo.enumType.InvitationResponseType;
import com.footballer.rest.api.v2.manager.EvaluationManagerV2;
import com.footballer.rest.api.v2.manager.EventCommentsManager;
import com.footballer.util.JacksonUtil;
import com.footballer.util.ObjectUtil;
import com.google.common.collect.Lists;

@Path("/mobile/v1/event-service")
public class EventService {
	
	private static Logger logger = LoggerFactory.getLogger(EventService.class);
	
	@Autowired
	private EventDao eventDao;
	
    @Autowired
    public EventManager eventMgr;
    
	@Autowired
	private EventCommentsManager ecManager;
	
	@Autowired
	public NotificationManager notificationManager;
	
	@Autowired
    public UserManager userManager;
	
	@Autowired
	private EvaluationManagerV2 evaluationManagerV2;
	
	
    @POST
    @Path("/createEvent/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
	public JsonResponse createEvent(CreateEventRequest request) {
    	JsonResponse response = new JsonResponse();
    	Event event = request.getEvent();
    	event.setIsConfirmed(false);
    	List<Long> playerIds = request.getPlayerIds();
    	playerIds.add(event.getOwnerId());
    	
    	String message = event.toMessage();
    	try{
	    	eventDao.createEvent(event, playerIds);
	    	
	    	
	    	logger.debug("create event and send notification start ...");
	    	notificationManager.sendMessage(message, playerIds);
	    	logger.debug("create event and send notification end ...");
	    	response.setMessage("save event success");
	    	response.setStatus(JsonResponse.SUCCESS);
	        return response;
    	}catch(Exception e){
    		logger.error("createEvent error,param :"+JacksonUtil.toJson(request),e);
    		response.setMessage(e.getMessage());
        	response.setStatus(JsonResponse.ERROR);
            return response;
    	}
    	
	}
    
    //再次邀请其他球员参加指定赛事
    @POST
    @Path("/inviteAnotherPlayersOfEvent/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
	public JsonResponse inviteAnotherPlayersOfEvent(CreateEventRequest request) {
    	JsonResponse response = new JsonResponse();
    	Long eventId = request.getEventId();
    	List<Long> playerIds = request.getPlayerIds();
    	try{
	    	Event event =eventDao.findById(Event.class, eventId);
	    	if(eventMgr.checkEventAndPlayersExists(eventId, playerIds)){
	    		String message = event.toMessage();
	    		eventDao.createEventInvitations(eventId, playerIds,InvitationResponseType.NOT_RESPONSE);
	        	notificationManager.sendMessage(message, playerIds);
	        	response.setMessage("save event success");
	        	response.setStatus(JsonResponse.SUCCESS);
	        }else{
	        	response.setMessage("当前娱乐约球事件不存在或 邀请的球员不存在");
	        	response.setStatus(JsonResponse.ERROR);
	        }
    	}catch(Exception e){
    		logger.error("inviteAnotherPlayersOfEvent error,param :"+JacksonUtil.toJson(request),e);
    		response.setMessage(e.getMessage());
        	response.setStatus(JsonResponse.ERROR);
            return response;
    	}
        return response;
	}
    
  //再次邀请其他球员直接报名参加指定赛事
    @POST
    @Path("/inviteAnotherPlayersDirectlyJoinEvent/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
	public JsonResponse inviteAnotherPlayersDirectlyJoinEvent(CreateEventRequest request) {
    	JsonResponse response = new JsonResponse();
    	Long eventId = request.getEventId();
    	List<Long> playerIds = request.getPlayerIds();
    	Event event =eventDao.findById(Event.class, eventId);
    	try{
	    	if(eventMgr.checkEventAndPlayersExists(eventId, playerIds)){
	    		String message = event.toMessage();
	    		eventDao.createEventInvitations(eventId, playerIds,InvitationResponseType.ACCEPT);
	        	notificationManager.sendMessage(message, playerIds);
	        	response.setMessage("save event success");
	        	response.setStatus(JsonResponse.SUCCESS);
	        }else{
	        	response.setMessage("当前娱乐约球事件不存在或 邀请的球员不存在");
	        	response.setStatus(JsonResponse.ERROR);
	        }
    	}catch(Exception e){
			logger.error("inviteAnotherPlayersDirectlyJoinEvent error,param :"+JacksonUtil.toJson(request),e);
			response.setMessage(e.getMessage());
	    	response.setStatus(JsonResponse.ERROR);
	        return response;
    	}
        return response;
	}
    
    @POST
    @Path("/updateInvitation/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse updateInvitation(EventInvitation eventInvitaion) {
    	JsonResponse response = new JsonResponse();
    	try{
	    	if(eventDao.updateEventInviteStatus(eventInvitaion)){
	    		response.setMessage("saveorUpdate event success");
	        	response.setStatus(JsonResponse.SUCCESS);
	        	
	        	//String message = eventDao.getEventInvitationMessage(eventInvitaion);
	        	//notificationManager.sendMessage(message, eventDao.findPlayerIdByEventId(eventInvitaion.getEventId()));
	    	}else{
	    		response.setMessage("saveorUpdate event fail. eventId or playerId can not find in database");
	        	response.setStatus(JsonResponse.ERROR);
	    	}
    	}catch(Exception e){
			logger.error("updateInvitation error,param :"+JacksonUtil.toJson(eventInvitaion),e);
			response.setMessage(e.getMessage());
	    	response.setStatus(JsonResponse.ERROR);
	        return response;
    	}
        return response;
    }
    
    //签到
    @POST
    @Path("/playerCheckIn/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse playerCheckIn(PlayerCheckInRequest request) throws DomainNotFoundException {
    	Account account = AppContextHolder.getContext().getAccount();
    	SignResponse response = new SignResponse();
    	
    	if (ObjectUtil.isNull(request)|| 
    			ObjectUtil.isNull(request.getEventId())||
    			ObjectUtil.isNull(request.getLongitude())||
    			ObjectUtil.isNull(request.getLatitude())) {
    		logger.warn("eventId/playerId/longitude/latitdude  can not be null");
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("eventId/playerId/longitude/latitdude  can not be null");
			return response;
		}
    	try{
	    	if(eventMgr.checkPlayerIsInEventFieldPosition(request.getEventId(), request.getLongitude(), request.getLatitude())){
	    		Date signTime = new Date();
	    		EventInvitation ei = new EventInvitation();
	    		ei.setEventId(request.getEventId());
	    		ei.setPlayerId(account.getId());
	    		ei.setSigned(true);
	    		eventDao.updateEventInviteStatus(ei);
	    		response.setPlayerTeamActivityType(eventMgr.recordPlayerEventActivity(request.getEventId(), account.getId(), signTime));
	        	response.setMessage("到场签到成功");
	            response.setStatus(JsonResponse.SUCCESS);
				notificationManager.sendSingleMessage(
						NotificationMessage.getBeforeGameSignMessage(
								request.getEventId(), account.getId()),
						account.getId());
	    	}else{
	    		response.setMessage("抱歉，你并未到达球场附近，不能完成签到。请到达球场后再完成签到");
	        	response.setStatus(JsonResponse.ERROR);
	    	}
	    }catch(Exception e){
			logger.error("updateInvitation error,param :"+JacksonUtil.toJson(request),e);
			response.setMessage(e.getMessage());
	    	response.setStatus(JsonResponse.ERROR);
	        return response;
		}
        return response;
    }
    
    //获取所有用户所有内部赛事列表两个有内
    @GET
	@Path("/findEventsByPlayerId/{player_id}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public EventResponse findEventsByPlayerId(@PathParam("player_id") Long player_id) {

    	EventResponse response = new EventResponse();

		if (ObjectUtil.isNull(player_id)) {
			logger.warn("player id can not be null");
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("player id can not be null");
			return response;
		}
		try{
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			cal.add(Calendar.MONTH, -2);
			List<EventResult> eventList = eventDao.findAllGameEventsByPlayerId(player_id, cal.getTime());
			response.setEventType(EventType.footballGame);
			response.setEventList(eventList);
			response.setStatus(JsonResponse.SUCCESS);
		}catch(Exception e){
			logger.error("findEventsByPlayerId error,param :player_id;"+player_id,e);
			response.setMessage(e.getMessage());
	    	response.setStatus(JsonResponse.ERROR);
	        return response;
		}
		return response;
	}
    
    
    //获取所有用户所有内部赛事列表两个月前
    @GET
	@Path("/findEventsHistoryByPlayerId/{player_id}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public EventResponse findEventsHistoryByPlayerId(@PathParam("player_id") Long player_id) {

    	EventResponse response = new EventResponse();

		if (ObjectUtil.isNull(player_id)) {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("player id can not be null");
			return response;
		}
		try{
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			cal.add(Calendar.MONTH, -2);
			List<EventResult> eventList = eventDao.findAllGameEventsHistoryByPlayerId(player_id, cal.getTime());
			response.setEventType(EventType.footballGame);
			response.setEventList(eventList);
			response.setStatus(JsonResponse.SUCCESS);
		}catch(Exception e){
			logger.error("findEventsHistoryByPlayerId error,param :player_id;"+player_id,e);
			response.setMessage(e.getMessage());
	    	response.setStatus(JsonResponse.ERROR);
		}
		return response;
	}
    
    //获取用户 最近一次赛事信息用于展示在首页的最新赛事里
    @GET
	@Path("/findLatestEventByPlayerId/{player_id}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public EventResponse findLatestEventByPlayerId(@PathParam("player_id") Long player_id) throws DomainNotFoundException {

    	EventResponse response = new EventResponse();

		if (ObjectUtil.isNull(player_id)) {
			logger.warn("player id can not be null");
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("player id can not be null");
			return response;
		}


		try{
			EventResult eventResult = eventDao.findLatestEventByPlayerId(player_id);
			if(ObjectUtil.isNotNull(eventResult)){
				response.setEventResult(eventResult);
				response.setEventType(eventResult.getEvent().getType());
				if(eventResult.getEvent().getType() == EventType.footballGame){
					//内部赛只需要返回参赛人员名单
					response.setPlayerList(eventDao.findPlayerInfosByEventId(eventResult.getEvent().getId()));
				}else if (eventResult.getEvent().getType() == EventType.footballMatch){
					//TODO  正式比赛需要返回两支球队的情况
	//				response.setHostTeam(hostTeam);
	//				response.setGuestTeam(guestTeam);
				}
				response.setStatus(JsonResponse.SUCCESS);
			}else{
				response.setStatus(JsonResponse.NODATA);
				response.setMessage("无最新赛事");
			}
		}catch(Exception e){
			logger.error("findEventsHistoryByPlayerId error,param :player_id;"+player_id,e);
			response.setMessage(e.getMessage());
	    response.setStatus(JsonResponse.ERROR);
	  }
		return response;
	}
    
    @GET
	@Path("/findEventInfoByEventId/{event_id}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public EventResponse findEventInfoByEventId(@PathParam("event_id") Long event_id) {

    	EventResponse response = new EventResponse();

		if (ObjectUtil.isNull(event_id)) {
			logger.warn("event id can not be null");
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("event id can not be null");
			return response;
		}
		try{
			EventResult eventResult = eventDao.findEventResultByEventId(event_id);
			if(ObjectUtil.isNull(eventResult)){
				response.setMessage("current eventId is not exist");
				return response;
			}
			List<PlayerWithStatus> playerStatusList = Lists.newArrayList();
			playerStatusList = eventDao.findPlayerInfosByEventId(event_id);
			
			response.setEventResult(eventResult);
			response.setPlayerList(playerStatusList);
			response.setEventType(EventType.footballGame);
			
			switch(eventResult.getStatus()) {
			
				case INIT:
					break;
				case STARTSIGNIN:
					break;	
				case REGCLOSE:
					break;
				case GAMEBEGIN:
					break;
				case CAPCONFIRM: //获取所有参赛球员互评消息
								 response.setPlayerEventEvaluationList(evaluationManagerV2.findDashboard(event_id).getEvaluations());
								 break;
								 
				case EVALUATION: //获取所有球员扣款情况和 出席情况
					 			 response.setPlayerActivityWithChargeFeeList(userManager.findPlayerActivityAndChargeFee(playerStatusList, event_id));
					             //获取所有参赛球员互评消息
								 response.setPlayerEventEvaluationList(evaluationManagerV2.findDashboard(event_id).getEvaluations());
								 break;		
			
				case END:   	 //获取所有球员扣款情况和 出席情况
								 response.setPlayerActivityWithChargeFeeList(userManager.findPlayerActivityAndChargeFee(playerStatusList, event_id));
								 //获取所有参赛球员互评消息
								 response.setPlayerEventEvaluationList(evaluationManagerV2.findDashboard(event_id).getEvaluations());
								 break;	
				case UNKOWN:
					break;
					
				default:		 //获取留言列表
								 response.setEcList(ecManager.getEventCommentsList(event_id));
								 break;
				
			}
			response.setStatus(JsonResponse.SUCCESS);
			return response;
	    }catch(Exception e){
			logger.error("findEventsHistoryByPlayerId error,param :event_id;"+event_id,e);
			response.setMessage(e.getMessage());
	    	response.setStatus(JsonResponse.ERROR);
	    	return response;
		}
	}
}
