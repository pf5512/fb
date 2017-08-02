package notification;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.footballer.filter.AppContextHolder;
import com.footballer.filter.context.ApplicationContext;
import com.footballer.notification.NotificationManager;
import com.footballer.rest.api.v1.dao.EventDao;
import com.footballer.rest.api.v1.vo.Account;
import com.footballer.rest.api.v1.vo.PlayerAppToken;
import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
		"classpath:applicationContext-injectBeans.xml",
		"classpath:applicationContext-redis.xml"})
public class IOSNoficationTest {
	
	@Autowired
	public EventDao eventDao;
	
	@Autowired
	public NotificationManager notificationManager;
	
	private static final Long ACCOUNT_ID = 1L;
	
	@BeforeClass
	public static void setup() {
		Account account = new Account();
		account.setId(ACCOUNT_ID);
		account.setName("Unit Test");
		account.setStatus("active");
		ApplicationContext context = new ApplicationContext(account);
		AppContextHolder.setContext(context);
	}
	
	@AfterClass
	public static void tearDown() {
		AppContextHolder.clearContext();
	}
	
	private static final List<Long> playerIds = Lists
			.newArrayList(
					ACCOUNT_ID,    //test
					136L  //ian
					); 	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testFindDeviceTokenByPlayerId() {
		Set<PlayerAppToken> list = eventDao.findDeviceTokens(new HashSet<Long>(playerIds));
		Assert.assertEquals(1, list.size());
	}
	
	@Test
	public void testCreateEventSendNotification() {
		int sendAccount = notificationManager.sendMessage("footballer UT", playerIds);
		Assert.assertEquals(1, sendAccount);
	}

}
