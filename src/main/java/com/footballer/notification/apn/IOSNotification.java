package com.footballer.notification.apn;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

/**
 * 
 * @author ianjiang
 *
 */
public class IOSNotification{
	
	private static final String CERTIFICATE_FILE_PATH = "/ios/aps_live_identity.p12";
	//aps_live_identity.p12
	//aps_developer_identity.p12
	//private static final String CERTIFICATE_FILE_PATH = "/Users/ian/dev/dev/fb/soccer/dev_fb/src/main/java/com/footballer/notification/apn/aps_live_identity.p12";
	//private static final String CERTIFICATE_FILE_PATH = "/Users/ian/dev/dev/fb/soccer/dev_fb/src/main/java/com/footballer/notification/apn/final3.p12";
	private static final String CERTIFICATE_PASSWORD = "098765";
	
	private static ApnsService service = 
			APNS.newService().withCert(CERTIFICATE_FILE_PATH, CERTIFICATE_PASSWORD)
		    //.withSandboxDestination()
			.withProductionDestination()
		    .build();

	public static void sendMessage(String token, String message) {
		System.out.println("IOS implementaion send notification start");
		System.out.println("token:" + token);
		System.out.println("message:" + message);
		String playload = APNS.newPayload().badge(1).alertBody(message).build();
		service.push(token, playload);
		System.out.println("IOS implementaion send notification done");
	}
	
	public static void sendMessage(Collection<String> tokens, String message) {
		System.out.println("BATCH: IOS implementaion send notification start");
		System.out.println("token size:" + tokens.size());
		System.out.println("message:" + message);
		String playload = APNS.newPayload().badge(1).alertBody(message).build();
		service.push(tokens, playload);
		System.out.println("BATCH: IOS implementaion send notification done");
	}
	
	public static void main(String[] args) {
		List<String> list = Lists
				.newArrayList(
						//"0e255840559e9fdb0a5abc99f6c259d985c59d46f60184d3075502d729fb2c89",  //arlin
						"ae64599f71f61971ca71ad6b5543d3ae6f3771872e53c59e1e83489aedf44675"//,  //ian
						//"93695bab7703a50c4eda317872ffb78641e768d1ee923eab4b9327f5fd9535eb"   //justin
						); 
		
		
		IOSNotification.sendMessage(list, "西瓜++");	
		
		//IOSNotification.sendMessage("ae64599f71f61971ca71ad6b5543d3ae6f3771872e53c59e1e83489aedf44675", "message");
		
		
		/*
		for (String token : list) {
			IOSNotification.sendMessage(token, "芒果");
		}
		*/
	}
}
