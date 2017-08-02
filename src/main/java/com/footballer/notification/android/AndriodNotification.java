package com.footballer.notification.android;

import java.util.Map;

import org.json.JSONObject;

import com.tencent.xinge.Message;
import com.tencent.xinge.XingeApp;

public class AndriodNotification {
	
	private static final Long ID = 2100115091L;
	private static final String KEY = "ade500a3573a42751de80b37fe46f938";
	//private static final String TOKEN = "e0b2f6f2202f54be40f29f3f45895de5cbae894c";
	
	public static JSONObject sendMessage(String token, String title, String message) {		
		return XingeApp.pushTokenAndroid(ID, KEY, title, message, token);
	}
	
	public static JSONObject sendMessage(String token, String title, Map<String, Object> map) {
		Message message = new Message();
		message.setTitle(title);
		message.setType(1);
		message.setContent((String) map.get("content"));
		message.setCustom(map);
		return new XingeApp(ID, KEY).pushSingleDevice(token, message);
	}	
}
