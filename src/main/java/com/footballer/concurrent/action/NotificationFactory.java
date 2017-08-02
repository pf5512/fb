package com.footballer.concurrent.action;

import java.util.Map;

import com.footballer.notification.android.AndriodNotification;
import com.footballer.notification.apn.IOSNotification;
import com.footballer.rest.api.v1.vo.PlayerAppToken;
import com.footballer.rest.api.v1.vo.enumType.AppTokenType;


public class NotificationFactory { 
		
	public static void send(PlayerAppToken token, String message) {
		if (AppTokenType.IOS.equals(token.getType())) {
			IOSNotification.sendMessage(token.getToken(), message);
		} else if (AppTokenType.ANDROID.equals(token.getType())) {
			AndriodNotification.sendMessage(token.getToken(), null, message);
		}
	}
	
	public static void send(PlayerAppToken token, Map<String, Object> map) {
		if (AppTokenType.IOS.equals(token.getType())) {
			IOSNotification.sendMessage(token.getToken(), (String)map.get("content"));
		} else if (AppTokenType.ANDROID.equals(token.getType())) {
			AndriodNotification.sendMessage(token.getToken(), null, map);
		}
	}
}