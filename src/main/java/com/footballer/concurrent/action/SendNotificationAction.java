package com.footballer.concurrent.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveAction;

import com.footballer.rest.api.v1.dao.EventDao;
import com.footballer.rest.api.v1.vo.PlayerAppToken;

@SuppressWarnings("serial")
public class SendNotificationAction extends RecursiveAction {
	
	private Set<Long> playerIds;
	private String message;
	
	private EventDao eventDao;
	
	public SendNotificationAction(Set<Long> playerIds, String message, EventDao eventDao) {
		this.playerIds = playerIds;
		this.message = message;
		this.eventDao = eventDao;
	}

	@Override
	protected void compute() {
		System.out.println("start to execute send notification task start...");
		System.out.println("start to find player ids start");
		
		for (Long playerId : playerIds) {
			System.out.println("player id:" + playerId);
		}
		
		Set<PlayerAppToken> tokens = eventDao.findDeviceTokens(playerIds);
		System.out.println("start to find player ids end");
		System.out.println("find token size:" + tokens.size());
		
		List<SendSingleNotificationAction> actions = new ArrayList<>();
		
		for (PlayerAppToken token : tokens) {
			if (null != token) {
				System.out.println("--> add token to action:" + token);
				SendSingleNotificationAction action = new SendSingleNotificationAction(token, message);
				actions.add(action);
			} else {
				System.out.println("token is null");
			}
		}
		
		System.out.println("start to fork send notification task, task size:" + tokens.size());
		invokeAll(actions);
		
		System.out.println("start to execute send notification task done ...");
	}	

}
