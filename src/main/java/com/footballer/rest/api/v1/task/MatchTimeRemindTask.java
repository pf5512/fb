package com.footballer.rest.api.v1.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.filter.AppContextHolder;
import com.footballer.filter.context.ApplicationContext;
import com.footballer.notification.NotificationManager;
import com.footballer.rest.api.v1.dao.ArenaDao;
import com.footballer.rest.api.v1.dao.EventDao;
import com.footballer.rest.api.v1.dao.FieldDao;
import com.footballer.rest.api.v1.exception.DomainNotFoundException;
import com.footballer.rest.api.v1.vo.Account;
import com.footballer.rest.api.v1.vo.Arena;
import com.footballer.rest.api.v1.vo.Event;
import com.footballer.rest.api.v1.vo.Field;



public class MatchTimeRemindTask {

	@Autowired
	public EventDao eventDao;
	
	@Autowired
	public FieldDao fieldDao;
	
	@Autowired
	public ArenaDao arenaDao;
	
	@Autowired
	public NotificationManager notificationManager;
	
	public void test() {
	    System.out.println("==> 启动定时任务 ==>");
	}
	 
	 
	public void matchTimePreReminder() throws DomainNotFoundException{
		 /**
		  * 1 获取系统当前时间 currentTime
		  * 2 读取数据库 比赛开始时间在当前时间同一天的 并且 在currentTime 2h-2.5h 之间的所有比赛事件 allTodayEvent（因为设置的半小时检测一次）
		  * 3 循环 allTodayEvent，取出每个时间的所有参加的队员，发送提示推送：你的XX比赛，将于 2个小时候开始，请提前到达球场热身
		  */
		 //因为是服务器直接call，这里设置为默认 account id =0
		System.out.println("================================> 启动 自动发送赛前推送 任务 >==============================================");
		 Account account = new Account();
		 account.setId(0L);
		 AppContextHolder.setContext(new ApplicationContext(account));
		 
		 Date currentTime = new Date(); 
		 List<Event> events = eventDao.findPreTimeEventsByTime(currentTime);
		 for(Event e :events){
			 List<Long> playerIds = eventDao.findPlayerIdByEventId(e.getId());
			 Field field = fieldDao.findById(Field.class, e.getFieldId());
			 Arena arena = arenaDao.findById(Arena.class, field.getArena_id());
		     String fieldAddress = arena.getAddressName() +" - "+ field.getName(); 
			 String message = "赛事提醒： 您参与的赛事将于2小时内正式开始，请提前到球场:"+fieldAddress+"  签到和热身";
			 notificationManager.sendMessage(message, playerIds);
		 }
		 
		 List<Event> endEvents = eventDao.findAfterTimeEventsByTime(currentTime);
		 for(Event e :endEvents){
			 Long captainId = e.getOwnerId();
			 String captainMessage = "赛事提醒：队长，您组织参与的赛事已结束，请确认球员出场和扣款情况，参与球员互评";
			 notificationManager.sendSingleMessage(captainMessage, captainId);
			 
			 List<Long> playerIds = eventDao.findPlayerIdByEventId(e.getId());
			 String message = "赛事提醒： 您参与的赛事刚刚结束了，快去欢快的评价（tiaoxi）队友们吧";
			 notificationManager.sendMessage(message, playerIds);
		 }
		 
		 System.out.println("================================> 结束 自动发送赛前推送 任务 >==============================================");
	 }
}
