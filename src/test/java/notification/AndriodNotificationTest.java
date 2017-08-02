package notification;

import junit.framework.Assert;

import org.json.JSONObject;
import org.junit.Test;

import com.footballer.notification.android.AndriodNotification;
import com.tencent.xinge.XingeApp;

public class AndriodNotificationTest {
	
	private static final Long ID = 2100115091L;
	private static final String KEY = "ade500a3573a42751de80b37fe46f938";
	private static final String TOKEN = "e0b2f6f2202f54be40f29f3f45895de5cbae894c";
	
	@Test
	public void testAndriodSendMessage() {
		JSONObject response = XingeApp.pushTokenAndroid(ID, KEY, "标题", "大家好!", TOKEN);
		Assert.assertEquals(0, response.getInt("ret_code"));
	}
	
	@Test
	public void testAndriodNotification() {
		JSONObject response = AndriodNotification.sendMessage(TOKEN, "踢球者", "我的足球生涯!");
		Assert.assertEquals(0, response.getInt("ret_code"));
	}
}
