package com.footballer.concurrent.action;

import java.util.concurrent.RecursiveAction;

import com.footballer.rest.api.v1.vo.PlayerAppToken;

@SuppressWarnings("serial")
public class SendSingleNotificationAction extends RecursiveAction {
	
	private PlayerAppToken token;
	private String message;
	
	public SendSingleNotificationAction(PlayerAppToken token, String message) {
		this.token = token;
		this.message = message;
	}

	@Override
	protected void compute() {
		System.out.println("SendSingleNotificationAction send notification");
		System.out.println("message:" + message);
		
		NotificationFactory.send(token, message);
		
		System.out.println("SendSingleNotificationAction send done.");
	}

}
