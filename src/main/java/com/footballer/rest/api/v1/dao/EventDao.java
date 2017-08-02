package com.footballer.rest.api.v1.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DateType;
import org.hibernate.type.DbTimestampType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.footballer.filter.AppContextHolder;
import com.footballer.rest.api.v1.domain.PlayerWithStatus;
import com.footballer.rest.api.v1.exception.DomainNotFoundException;
import com.footballer.rest.api.v1.result.AddressResult;
import com.footballer.rest.api.v1.result.EventResult;
import com.footballer.rest.api.v1.result.OwnerResult;
import com.footballer.rest.api.v1.vo.Address;
import com.footballer.rest.api.v1.vo.Arena;
import com.footballer.rest.api.v1.vo.Event;
import com.footballer.rest.api.v1.vo.EventInvitation;
import com.footballer.rest.api.v1.vo.Field;
import com.footballer.rest.api.v1.vo.Player;
import com.footballer.rest.api.v1.vo.PlayerAppToken;
import com.footballer.rest.api.v1.vo.PlayerTeamActivity;
import com.footballer.rest.api.v1.vo.Team;
import com.footballer.rest.api.v1.vo.enumType.EventStatus;
import com.footballer.rest.api.v1.vo.enumType.EventType;
import com.footballer.rest.api.v1.vo.enumType.InvitationResponseType;
import com.footballer.util.CommonUtil;
import com.footballer.util.CopyUtil;
import com.footballer.util.DateUtil;
import com.footballer.util.ObjectUtil;
import com.google.common.collect.Lists;
//import com.footballer.notification.apn.IOSNotification;


public class EventDao extends BaseDao {
	
	@Autowired
	public ArenaDao arenaDao;
	
	@Autowired
	public FieldDao fieldDao;
	
	@Autowired
	public AddressDao addressDao;
	
	@Autowired
	public UserDao userDao;
	
	@Autowired
	public PlayerDao playerDao;
	
	@Autowired
	public TeamDao teamDao;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void createEvent(Event event, List<Long> playerIds) {		
		Session session = getSessionFactory().getCurrentSession();
		CommonUtil.addAuditableAttributes(event);
		session.saveOrUpdate(event);
        session.flush();
		
        // add creator to the event_invitation and accept by default.
        EventInvitation own_invitation = new EventInvitation();
        own_invitation.setEventId(event.getId());
        own_invitation.setPlayerId(AppContextHolder.getContext().getAccount().getId());
        own_invitation.setStatus(InvitationResponseType.OWNER);			
		session.save(own_invitation);
		
		// delete duplicated player id
		Set<Long> playerList = new HashSet<>(playerIds);
		playerList.remove(AppContextHolder.getContext().getAccount().getId());
        int i = 0;
        
        // Batch insert
		for (Long playerId : playerList) {
			i++;
			
			EventInvitation invitation = new EventInvitation();
			invitation.setEventId(event.getId());
			invitation.setPlayerId(playerId);
			invitation.setSigned(false);
			invitation.setStatus(InvitationResponseType.NOT_RESPONSE);			
			session.save(invitation);
			
			System.out.println("invitation:" + invitation.toString());
			
			if (i % 50 == 0) {
				session.flush();
				session.clear();
			}
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void createEventInvitations(Long eventId,List<Long> players,InvitationResponseType type) {
		int i = 0;
		List<Long> existInvitationPlayerList = Lists.newArrayList();
		
        // Batch insert
		for (Long playerId : players) {
			i++;
			EventInvitation invitation = new EventInvitation();
			invitation.setEventId(eventId);
			invitation.setPlayerId(playerId);
			invitation.setStatus(type);			
			try {
				save(invitation);
			} catch (DataAccessException e) {
				System.out.println("已存在这次邀请 eventId:"+eventId+" playerId:"+playerId+" 更新~~");
				existInvitationPlayerList.add(playerId);
				clear();
			}
			System.out.println("invitation:" + invitation.toString());
			if (i % 50 == 0) {
				flush();
				clear();
			}
		}
		
		//如果存在已经存在的邀请 那么进行更新操作
		for(Long existPlayerId: existInvitationPlayerList)
		{
			Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(EventInvitation.class);
			criteria.add(Restrictions.eq("playerId", existPlayerId))
			             .add(Restrictions.eq("eventId", eventId));
			EventInvitation ei = (EventInvitation) criteria.uniqueResult();
			ei.setStatus(type);
			update(ei);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public Set<PlayerAppToken> findDeviceTokens(Set<Long> playerIds) {
		System.out.println("start to find device token");
		
		long beginTime = System.currentTimeMillis();
		System.out.println("Event dao to find device token: " + beginTime);
		
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(PlayerAppToken.class);
		
		if (!CollectionUtils.isEmpty(playerIds)) {
			criteria.add(Restrictions.in("playerId", new HashSet<>(playerIds)));
		}
		
		@SuppressWarnings("unchecked")
		List<PlayerAppToken> playerAppTokens = criteria.list();
		
		return new HashSet<>(playerAppTokens);		
	}
	
	//获取 赛事 邀请的球员们ids
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<Long> findPlayerIdByEventId(Long eventId) {
		List<Long> playerIds = new ArrayList<>();
		Long currentPlayerId = AppContextHolder.getContext().getAccount().getId();
		
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(EventInvitation.class);
		criteria.add(Restrictions.eq("eventId", eventId));
		
		@SuppressWarnings("unchecked")
		List<EventInvitation> invitations = criteria.list();
		
		for (EventInvitation invitation : invitations) {
			//exclude current account player
			if (!currentPlayerId.equals(invitation.getPlayerId())) {
				playerIds.add(invitation.getPlayerId());
			}
		}
		
		return playerIds;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public EventInvitation findEventInvitationByEventIdAndPlayerId(Long eventId,Long playerId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(EventInvitation.class);
		criteria.add(Restrictions.eq("eventId", eventId))
				.add(Restrictions.eq("playerId", playerId));
		
		return (EventInvitation) criteria.uniqueResult();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public String getEventInvitationMessage(EventInvitation eventInvitaion) {
		Event event = findById(Event.class, eventInvitaion.getEventId());
		Player player = findById(Player.class, eventInvitaion.getPlayerId());
		
		return player.getAccount().getName() + "," + eventInvitaion.getStatus() + " -> " + event.toMessage();
	}
		
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public boolean updateEventInviteStatus(EventInvitation eventInvitaion) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(EventInvitation.class);
		criteria.add(Restrictions.eq("eventId", eventInvitaion.getEventId()))
				.add(Restrictions.eq("playerId", eventInvitaion.getPlayerId()));
		
		EventInvitation eventInvitation =  (EventInvitation) criteria.uniqueResult();
		if(ObjectUtil.isNotNull(eventInvitation)){
			InvitationResponseType status = eventInvitaion.getStatus();
			if(status != null || "".equals(status)){
				eventInvitation.setStatus(status);
				eventInvitation.setFriendsNo(eventInvitaion.getFriendsNo());
				eventInvitation.setReplyTime(new Date());
			}
			Boolean isSigned = eventInvitaion.getSigned();
			if(isSigned != null || "".equals(isSigned)){
				eventInvitation.setSigned(isSigned);
				eventInvitation.setSignTime(new Date());
			}
			Boolean isAdmin = eventInvitaion.getAdmin();
			if(isAdmin != null || "".equals(isAdmin)){
				eventInvitation.setAdmin(isAdmin);
				//eventInvitation.setReplyTime(new Date());
			}
			this.save(eventInvitation);
			return true;
		}
		return false;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	@SuppressWarnings("unchecked")
	public List<EventResult> findOwnedGameEventsByPlayerId(Long player_id)
	{
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Event.class);
		criteria.add(Property.forName("ownerId").eq(player_id));
		criteria.add(Restrictions.eq("type", EventType.footballGame.toString()));
		criteria.addOrder(Order.desc("id"));
		List<Event> ownedEvents= criteria.list();
		
		List<EventResult> eventResultList = Lists.newArrayList();
		for(Event e: ownedEvents){
//			EventResult eR = new EventResult();
//			eR.setEvent(e);
//			Field field = fieldDao.findById(Field.class, e.getFieldId());
//			Arena arena = arenaDao.findById(Arena.class, field.getArena_id());
//			String fieldAddress = arena.getAddressName() +" - "+ field.getName() +" - "+ field.getNumber(); 
//			eR.setFieldAddress(fieldAddress);
			
			EventResult eR = convertEventToEventResult(e);
			eventResultList.add(eR);
		}
		return eventResultList;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	@SuppressWarnings("unchecked")
	public List<EventResult> findJoinedGameEventsByPlayerId(Long player_id)
	{
		Criteria e_criteria = getSessionFactory().getCurrentSession().createCriteria(Event.class,"e");
		DetachedCriteria ei_criteria = DetachedCriteria.forClass(EventInvitation.class, "ei");
		ei_criteria.add(Property.forName("ei.eventId").eqProperty("e.id"));
		ei_criteria.add(Restrictions.eq("playerId", player_id));
		ei_criteria.add(Restrictions.eq("status", InvitationResponseType.ACCEPT.toString()));
		e_criteria.add(Restrictions.eq("type", EventType.footballGame.toString()));
		e_criteria.addOrder(Order.desc("id"));
		e_criteria.add(Subqueries.exists(ei_criteria.setProjection(Projections.property("ei.id"))));
		List<Event> joinedEvents= e_criteria.list();
		
		List<EventResult> eventResultList = Lists.newArrayList();
		for(Event e: joinedEvents){
//			EventResult eR = new EventResult();
//			eR.setEvent(e);
//			Field field = fieldDao.findById(Field.class, e.getFieldId());
//			Arena arena = arenaDao.findById(Arena.class, field.getArena_id());
//			eR.setArena(arena);
//			Address address = addressDao.findById(Address.class, arena.getAddressId());
//			AddressResult addressResult = new AddressResult();
//			CopyUtil.copyForcedly(addressResult, address);
//			eR.setAddress(addressResult);
//			String fieldAddress = arena.getAddressName() +" - "+ field.getName() +" - "+ field.getNumber(); 
//			eR.setFieldAddress(fieldAddress);
			EventResult eR = convertEventToEventResult(e);
			eventResultList.add(eR);
		}
		return eventResultList;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	@SuppressWarnings("unchecked")
	public List<EventResult> findAllGameEventsByPlayerId(Long player_id, Date startDate)
	{
		String sql ="select e.owner_id as ownerId,e.id as id ,e.type,e.start_date ,e.end_date  ,e.field_id as fieldId ,e.player_count as playerCount , e.cost,e.isConfirmed,e.reply_end_date as replyEndDate " +
				"  from event e" +
				"	 where type = :type " +
				"		and ( exists (" +
				"				select ei.id from  event_invitation ei " +
				"					 where  ei.player_id = :player_id " +
				"						and ei.status <> :status " +
				"						and ei.event_id = e.id " +
				"				 ) or owner_id = :owner_id ) " +
				"		and e.start_date >= :start_date " +
				"		order by e.start_date desc   ";
		
		Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addScalar("id",  LongType.INSTANCE)
				.addScalar("ownerId",  LongType.INSTANCE)
				.addScalar("start_date",DbTimestampType.INSTANCE)
				.addScalar("end_date", DbTimestampType.INSTANCE)
				.addScalar("fieldId", LongType.INSTANCE)
				.addScalar("playerCount", IntegerType.INSTANCE)
				.addScalar("cost",IntegerType.INSTANCE)
				.addScalar("isConfirmed",BooleanType.INSTANCE)
				.addScalar("replyEndDate",DbTimestampType.INSTANCE)
				.addScalar("type",StringType.INSTANCE)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setParameter("type",  EventType.footballGame.name())
				.setParameter("player_id", player_id)
				.setParameter("status",  InvitationResponseType.OWNER.name())
				.setParameter("owner_id", player_id)
				.setParameter("start_date", startDate);
		
//		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Event.class);
//		criteria.add(Property.forName("ownerId").eq(player_id));
//		criteria.add(Restrictions.eq("type", EventType.footballGame));
//		List<Event> ownedEvents= criteria.list();
//		
//		Criteria e_criteria = getSessionFactory().getCurrentSession().createCriteria(Event.class,"e");
//		DetachedCriteria ei_criteria = DetachedCriteria.forClass(EventInvitation.class, "ei");
//		ei_criteria.add(Property.forName("ei.eventId").eqProperty("e.id"));
//		ei_criteria.add(Restrictions.eq("playerId", player_id));
//		ei_criteria.add(Restrictions.ne("status", InvitationResponseType.OWNER));
//		e_criteria.add(Restrictions.eq("type", EventType.footballGame));
//		e_criteria.add(Subqueries.exists(ei_criteria.setProjection(Projections.property("ei.id"))));
//		List<Event> joinedEvents= e_criteria.list();
//		
//		List<Event> events = Lists.newArrayList();
//		events.addAll(ownedEvents);
//		events.addAll(joinedEvents);
//		
//		Collections.sort(events, new Comparator<Event>(){
//			@Override
//			public int compare(Event e1, Event e2) {
//				if(ObjectUtil.isNull(e2.getStart_date()) || ObjectUtil.isNull(e1.getStart_date()) ){
//					return 0;
//				}else{
//					return e2.getStart_date().compareTo(e1.getStart_date());
//				}
//			}
//		});
		List<Map<String,Object>> events = query.list();
		List<EventResult> eventResultList = Lists.newArrayList();
		Event e = null;
		for(Map<String,Object> m: events){
			e = new Event();
			buildEvent(e, m);
			EventResult eR = convertEventToEventResult(e);
			eventResultList.add(eR);
		}
		return eventResultList;
	}

	private void buildEvent(Event e, Map<String, Object> m) {
		e.setId((Long)m.get("id"));
		e.setOwnerId((Long)m.get("ownerId"));
		e.setType(EventType.valueOf((String)m.get("type")));
		e.setStart_date((Date)m.get("start_date"));
		e.setEnd_date((Date)m.get("end_date"));
		e.setFieldId((Long)m.get("fieldId"));
		e.setPlayerCount((Integer)m.get("playerCount"));
		e.setCost((Integer)m.get("cost"));
		e.setIsConfirmed((Boolean)m.get("isConfirmed"));
		e.setReplyEndDate((Date)m.get("replyEndDate"));
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	@SuppressWarnings("unchecked")
	public List<EventResult> findAllGameEventsHistoryByPlayerId(Long player_id, Date startDate)
	{
		String sql ="select e.owner_id as ownerId,e.id as  id ,e.type,e.start_date ,e.end_date  ,e.field_id as fieldId ,e.player_count as  playerCount , e.cost,e.isConfirmed,e.reply_end_date as replyEndDate from event e" +
				"	 where type = :type  " +
				"		and ( exists (" +
				"				select ei.id from  event_invitation ei " +
				"					 where  ei.player_id = :player_id " +
				"						and ei.status <> :status" +
				"						and ei.event_id = e.id " +
				"				 ) or owner_id = :owner_id ) " +
				"		and e.start_date < :start_date" +
				"		order by e.start_date desc  ";
		
		
		Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addScalar("id",  LongType.INSTANCE)
				.addScalar("ownerId",  LongType.INSTANCE)
				.addScalar("start_date",DbTimestampType.INSTANCE)
				.addScalar("end_date", DbTimestampType.INSTANCE)
				.addScalar("fieldId", LongType.INSTANCE)
				.addScalar("playerCount", IntegerType.INSTANCE)
				.addScalar("cost",IntegerType.INSTANCE)
				.addScalar("isConfirmed",BooleanType.INSTANCE)
				.addScalar("replyEndDate",DbTimestampType.INSTANCE)
				.addScalar("type",StringType.INSTANCE)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setParameter("type",  EventType.footballGame.name())
				.setParameter("player_id", player_id)
				.setParameter("status",  InvitationResponseType.OWNER.name())
				.setParameter("owner_id", player_id)
				.setParameter("start_date", startDate);

		List<Map<String,Object>> events = query.list();
		List<EventResult> eventResultList = Lists.newArrayList();
		Event e = null;
		for(Map<String,Object> m: events){
			e = new Event();
			buildEvent(e, m);
			EventResult eR = convertEventToEventResult(e);
			eventResultList.add(eR);
		}
		return eventResultList;
	}
	
	private EventStatus getCurrentEventStatus(Event e){
		Date currentDate = DateUtil.getCurrentDate();
		Date twoHoursBeforeGameStart = DateUtil.addHoursOfParameterDate(e.getStart_date(), -2); 
		Date gameStart = e.getStart_date();
		Date gameOver = e.getEnd_date();
		Date evaluationTime = DateUtil.addHoursOfParameterDate(e.getEnd_date(), 48);
		
		if(e.getIsConfirmed() == null){
			return EventStatus.UNKOWN;
		}
		
		if(currentDate.before(e.getReplyEndDate())){
			return EventStatus.INIT;
		}else if(currentDate.after(e.getReplyEndDate()) && currentDate.before(twoHoursBeforeGameStart) ){
			return EventStatus.REGCLOSE;
		}else if(currentDate.after(twoHoursBeforeGameStart) && currentDate.before(e.getStart_date())){
			return EventStatus.STARTSIGNIN;
		}else if(currentDate.after(gameStart) && currentDate.before(gameOver)){
			return EventStatus.GAMEBEGIN;
		}else if(currentDate.after(gameOver) && !e.getIsConfirmed()){
			return EventStatus.CAPCONFIRM;
		}else if(currentDate.after(gameOver) && e.getIsConfirmed() && currentDate.before(evaluationTime)){
			return EventStatus.EVALUATION;
		}else if(currentDate.after(evaluationTime) && e.getIsConfirmed()){
			return EventStatus.END;
		}else{
			return EventStatus.UNKOWN;
		}
	}
	
	
	private EventResult convertEventToEventResult(Event e){
		
		EventResult eR = new EventResult();
		Long currentPlayerId = AppContextHolder.getContext().getAccount().getId();
		OwnerResult owner = new OwnerResult();
		Player player = playerDao.findPlayer(e.getOwnerId());
		owner.setId(e.getOwnerId());
		if(player !=null){
			owner.setName(player.getNickName());
			owner.setImgUrl(player.getImageUrl());
			owner.setTel(player.getAccount().getName());
		}
		Field field = fieldDao.findById(Field.class, e.getFieldId());
		Arena arena = arenaDao.findById(Arena.class, field.getArena_id());
		Address address = addressDao.findById(Address.class, arena.getAddressId());
		AddressResult addressResult = new AddressResult();
		CopyUtil.copyForcedly(addressResult, address);
		String fieldAddress = arena.getAddressName() +" - "+ field.getName() +" - "+ field.getNumber(); 
		EventInvitation ei = findEventInvitationByEventIdAndPlayerId(e.getId(),currentPlayerId);
		Integer joinedPlayerCount =  findJoinedPlayerCountOfEventByEventId(e.getId());
		Integer joinedPlayerSignedCount = findJoinedPlayerSignedCountOfEventByEventId(e.getId());
				
		eR.setOwner(owner);
		eR.setEvent(e); 
		eR.setArena(arena);
		eR.setField(field);
		eR.setAddress(addressResult);
		eR.setFieldAddress(fieldAddress);
		if(ObjectUtil.isNotNull(ei)){
			eR.setCurrentUserStatus(ei.getStatus());
		}
		eR.setJoinedPlayerCount(joinedPlayerCount);
		eR.setTotalPlayerCount(e.getPlayerCount());
		eR.setJoinedPlayerSignedCount(joinedPlayerSignedCount);
		eR.setStatus(getCurrentEventStatus(e));
		return eR;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	@SuppressWarnings("unchecked")
	public EventResult findLatestEventByPlayerId(Long player_id)
	{
		//创建的赛事
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Event.class);
		criteria.add(Property.forName("ownerId").eq(player_id));
		//criteria.add(Restrictions.eq("type", EventType.footballGame));
		List<Event> ownedEvents= criteria.list();
		
		//加入的赛事
		Criteria e_criteria = getSessionFactory().getCurrentSession().createCriteria(Event.class,"e");
		DetachedCriteria ei_criteria = DetachedCriteria.forClass(EventInvitation.class, "ei");
		ei_criteria.add(Property.forName("ei.eventId").eqProperty("e.id"));
		ei_criteria.add(Restrictions.eq("playerId", player_id));
		ei_criteria.add(Restrictions.ne("status", InvitationResponseType.OWNER));
		//e_criteria.add(Restrictions.eq("type", EventType.footballGame));
		e_criteria.add(Subqueries.exists(ei_criteria.setProjection(Projections.property("ei.id"))));
		List<Event> joinedEvents= e_criteria.list();
		
		List<Event> events = Lists.newArrayList();
		events.addAll(ownedEvents);
		events.addAll(joinedEvents);
		
		long betweenTime=9223372036854775806L;
		Event latestEvent = null;
		for(Event e: events)
		{
			long eventEndDateTime = e.getEnd_date().getTime();
			long currentDateTime = DateUtil.getCurrentDate().getTime();
			//在当前时间之后
			if(eventEndDateTime >  currentDateTime)
			{
				//然后找出距当前时间最近的赛事
				if(eventEndDateTime  -  currentDateTime < betweenTime ){
					betweenTime = eventEndDateTime  -  currentDateTime;
					latestEvent = e;
				}
			}
		}
		
		if(ObjectUtil.isNotNull(latestEvent)){
			return convertEventToEventResult(latestEvent);
		}else{
			return null;
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public EventResult findEventResultByEventId(Long eventId)
	{
		EventResult eventResult = null;
		Event e = findById(Event.class, eventId);
		if(ObjectUtil.isNotNull(e)){
			eventResult = convertEventToEventResult(e);
		}
		return eventResult;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<PlayerWithStatus> findPlayerInfosByEventId(Long eventId) throws DomainNotFoundException
	{
		Event e = findById(Event.class, eventId);
		Long currentEventsTeamId = e.getTeamId();
		
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(EventInvitation.class);
		criteria.add(Restrictions.eq("eventId", eventId));
		@SuppressWarnings("unchecked")
		List<EventInvitation> eiList = criteria.list();
		
		List<PlayerWithStatus> playerWithStatusList = Lists.newArrayList();
		
		List<PlayerTeamActivity> activities = teamDao.findPlayerEventActivities(eventId);
		Map<Long, PlayerTeamActivity> activityMap = new HashMap<>();
		
		//build activity map
		for (PlayerTeamActivity activity : activities) {
			activityMap.put(activity.getPlayerId(), activity);
		}
		
		//TODO: need to refine dao call in for loop
		for(EventInvitation ei: eiList) {
			PlayerWithStatus pws = new PlayerWithStatus();
			Player player = playerDao.findPlayer(ei.getPlayerId());
			
			pws.setId(player.getId());
			pws.setName(player.getNickName());
			pws.setImageUrl(player.getImageUrl());
			pws.setInvitationResponseType(ei.getStatus().toString());
			pws.setTakeFriendsNO(ei.getFriendsNo());
			pws.setSigned(ei.getSigned());
			pws.setAdmin(ei.getAdmin());
			pws.setIsInCurrentEventsTeam(checkPlayerIsInCurrentEventTeam(currentEventsTeamId, player.getTeams()));
			
			PlayerTeamActivity activity = activityMap.get(player.getId());
			if (null != activity) {
				pws.setPlayerTeamActivityType(activity.getType());
			}			
			playerWithStatusList.add(pws);
		}
		return playerWithStatusList;
	}
	
	private boolean checkPlayerIsInCurrentEventTeam(Long currentEventsTeamId,Set<Team> teams){
		boolean tag =false;
		for(Team team: teams){
			if(team.getId().toString().equals(currentEventsTeamId.toString()) ){
				tag = true;
				break;
			}
		}
		return tag;
	}
	
	//赛前2小时内推送赛事提醒
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<Event> findPreTimeEventsByTime(Date currentTime)
	{
		
		Date remindBeforeTime = DateUtil.addHoursOfCurrentTime(2);
		Date remindAfterTime = DateUtil.addHoursOfCurrentTime(2.5);
		System.out.println("系统当前时间 =>"+ currentTime);
		System.out.println("2小时之后的时间=>"+remindBeforeTime);
		System.out.println("2.5小时之后的时间=>"+remindAfterTime);
		
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Event.class);
		criteria.add(Restrictions.between("start_date", remindBeforeTime, remindAfterTime));
		//criteria.add(Restrictions.ge("start_date",remindBeforeTime));
		//criteria.add(Restrictions.le("start_date",remindAfterTime));
		return criteria.list();
	}
	
	//赛后半小时内推送赛事互评确认
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<Event> findAfterTimeEventsByTime(Date currentTime)
	{
		
		Date remindBeforeTime = DateUtil.addHoursOfCurrentTime(0.1);
		Date remindAfterTime = DateUtil.addHoursOfCurrentTime(0.6);
		System.out.println("系统当前时间 =>"+ currentTime);
		System.out.println("6分钟之后的时间=>"+remindBeforeTime);
		System.out.println("36分钟之后的时间=>"+remindAfterTime);
		
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Event.class);
		criteria.add(Restrictions.between("end_date", remindBeforeTime, remindAfterTime));
		//criteria.add(Restrictions.ge("start_date",remindBeforeTime));
		//criteria.add(Restrictions.le("start_date",remindAfterTime));
		return criteria.list();
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public Integer findJoinedPlayerCountOfEventByEventId(Long eventId)
	{
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(EventInvitation.class);
		criteria.add(Restrictions.eq("eventId", eventId))
				     .add(Restrictions.or(
								Restrictions.eq("status", InvitationResponseType.OWNER)
								,Restrictions.eq("status", InvitationResponseType.ACCEPT))
							);
		
		criteria.setProjection(Projections.rowCount ());
		return Integer.parseInt(criteria.uniqueResult().toString());
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public Integer findJoinedPlayerSignedCountOfEventByEventId(Long eventId)
	{
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(EventInvitation.class);
		criteria.add(Restrictions.eq("eventId", eventId))
				      .add(Restrictions.or(
								Restrictions.eq("status", InvitationResponseType.OWNER)
								,Restrictions.eq("status", InvitationResponseType.ACCEPT))
							)
		             .add(Restrictions.eq("signed",true));
		
		criteria.setProjection(Projections.rowCount ());
		return Integer.parseInt(criteria.uniqueResult().toString());
	}
}
