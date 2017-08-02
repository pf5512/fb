package com.footballer.rest.api.v2.manager;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footballer.rest.api.v1.dao.EventDao;
import com.footballer.rest.api.v1.dao.PlayerDao;
import com.footballer.rest.api.v1.vo.Event;
import com.footballer.rest.api.v1.vo.Player;
import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.result.EventCommentsResult;
import com.footballer.rest.api.v2.vo.EventComments;
import com.footballer.util.CommonUtil;
import com.footballer.util.ObjectUtil;
import com.google.common.collect.Lists;


@Service
public class EventCommentsManager {
	
	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	
	@Autowired
	public PlayerDao playerDao;
	
	public void createEventComments(long eventId, long playerId,String comments){
		EventComments ec = this.buildEventComments(eventId, playerId, comments);
		mybatisBaseDao.insert("saveEventComments", ec);
	}
	
	private EventComments buildEventComments(long eventId, long playerId,String comments) {
		EventComments ec = new EventComments();
		CommonUtil.addAuditableAttributes(ec);
		
		ec.setEventId(eventId);
		ec.setPlayerId(playerId);
		ec.setComments(comments);
		return ec;
	}
	
	@SuppressWarnings("unchecked")
	public List<EventComments> findEventCommentsByEventIdAndPlayerId(Long eventId, Long playerId){
		EventComments ec = new EventComments();
		ec.setEventId(eventId);
		ec.setPlayerId(playerId);
		List<EventComments> list = (List<EventComments>)mybatisBaseDao.selectList("findEventCommentsByEventIdAndPlayerId", ec);
		return list;
	}
	
	public EventComments findEventCommentsById(Long id){
		EventComments list = (EventComments)mybatisBaseDao.selectList("findEventCommentsById", id);
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<EventCommentsResult> getEventCommentsList(Long eventId) {
		List<EventCommentsResult> ecList= Lists.newArrayList();
		EventComments ec = new EventComments();
		ec.setEventId(eventId);
		
		ecList = (List<EventCommentsResult>) mybatisBaseDao.selectList("findEventCommentsListByEventId", ec);
		return ecList;
	}
	
	
	
}
