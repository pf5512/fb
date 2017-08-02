package com.footballer.rest.api.v2.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.filter.AppContextHolder;
import com.footballer.filter.context.ApplicationContext;
import com.footballer.notification.NotificationManager;
import com.footballer.rest.api.v1.exception.DomainNotFoundException;
import com.footballer.rest.api.v1.vo.Account;
import com.footballer.rest.api.v2.manager.TournamentManager;
import com.footballer.rest.api.v2.vo.TournamentRemindInfo;
import com.footballer.util.ListUtil;
import com.footballer.util.ObjectUtil;

import jersey.repackaged.com.google.common.collect.Lists;



public class TournamentMatchTimeRemindTask {

	
	@Autowired
	public NotificationManager notificationManager;
	
	@Autowired
	public TournamentManager tManager;
	
	public void test() {
	    System.out.println("==> 启动定时任务 ==>");
	}
	 
	private static final String B4M = "BEFOREMATCH";
	private static final String AFM = "AFTERMATCH";
	 
	@SuppressWarnings("unchecked")
	public void matchTimeReminder() throws DomainNotFoundException{
		//因为是服务器直接call，这里设置为默认 account id =0
		System.out.println("================================> 启动 自动发送赛前推送 任务 >==============================================");
		Account account = new Account();
		account.setId(0L);
		AppContextHolder.setContext(new ApplicationContext(account));
		 
		List<TournamentRemindInfo> tRemindInfoListB4Match = tManager.getTournamentRemindInfoListB4Match();
		if(ListUtil.checkListisNotNull(tRemindInfoListB4Match)){
			dataPrepareAndSendNotification(tRemindInfoListB4Match, B4M);
		}
		 
		List<TournamentRemindInfo> tRemindInfoListAfterMatch = tManager.getTournamentRemindInfoListAfterMatch();
		if(ListUtil.checkListisNotNull(tRemindInfoListAfterMatch)){
			dataPrepareAndSendNotification(tRemindInfoListAfterMatch, AFM);
		}
		 
		System.out.println("================================> 结束 自动发送赛前推送 任务 >==============================================");
	 }
	
	public void dataPrepareAndSendNotification(List<TournamentRemindInfo> remindList, String type){
		 List<String> teams = Lists.newArrayList();
		 HashMap <String, List<Long>>  playersMap= new HashMap<String, List<Long>>();
		 HashMap <String, String>  msgMap= new HashMap<String, String>();
		 
		 for(TournamentRemindInfo tri :remindList){
			 teams.add(tri.getTeams());
		 }
		 
		 teams = ListUtil.removeDuplicate(teams);
		 for(String  team :teams){
			 List<Long> playerIds = Lists.newArrayList();
			 String message = "";
			 for(TournamentRemindInfo tri :remindList){
				 if(team.equals(tri.getTeams())){
					 playerIds.add(tri.getPlayerId());
				 }
				 if(team.equals(tri.getTeams())){
					 if(type.equals(B4M)){
						 message = "Match Tip=>"
						 		 + " Hey SuperStar, "+tri.getTournamentName()+" - "+tri.getTeams()+" will kickoff at: "+tri.getArenaName()+" - "+ tri.getKickOffTime() +" please get there early and warm up :)";
					 }
					 if(type.equals(AFM)){
						 message = "Data stats & HighLights Tip=>"
						 		  +" Hey SuperStar, "+tri.getTournamentName()+" - "+tri.getTeams()+" - kickoff at:"+ tri.getKickOffTime() + "=> Data statistics And HighLights were updated. Go watch and Share :)";
					 }
					 break;
				 }
			 }
			 playersMap.put(team, playerIds);
			 msgMap.put(team, message);
		 }
		 
		 String msg="";
		 for (Entry<String, List<Long>> entryPlayer : playersMap.entrySet()) {  
			 for (Entry<String, String> entryMsg : msgMap.entrySet()) {  
				 if(entryMsg.getKey() == entryPlayer.getKey()){
					 msg = entryMsg.getValue();
					 break;
				 }
			 }
			 notificationManager.sendMessage(msg, entryPlayer.getValue());
		 }  
	}
}
