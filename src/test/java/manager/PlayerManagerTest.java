package manager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.filter.AppContextHolder;
import com.footballer.filter.context.ApplicationContext;
import com.footballer.rest.api.v1.manager.PlayerManager;
import com.footballer.rest.api.v1.vo.Account;
import com.footballer.rest.api.v1.vo.UserToken;
import com.footballer.rest.api.v1.vo.enumType.AppTokenType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
		"classpath:applicationContext-injectBeans.xml",
		"classpath:applicationContext-redis.xml",
		"classpath:dataSource.xml"})
@TransactionConfiguration(transactionManager = "transactionManager",defaultRollback = true)
@Transactional
public class PlayerManagerTest  extends AbstractTransactionalJUnit4SpringContextTests {
	
	private static final Long ACCOUNT_ID = 1L;
	@Autowired
	private PlayerManager playerManager;
	
	@BeforeClass
	public static void setup() {
		Account account = new Account();
		
		UserToken userToken = new UserToken();
		userToken.setIdentity("test-identity");
		
		account.setUserToken(userToken);
		
		account.setId(ACCOUNT_ID);
		account.setName("Unit Test");
		account.setStatus("active");
		ApplicationContext context = new ApplicationContext(account);
		context.setAppTokenType(AppTokenType.IOS);
		
		AppContextHolder.setContext(context);
	}
	
	@AfterClass
	public static void tearDown() {
		AppContextHolder.clearContext();
	}
	
	@Test
	public void testProcessEventConfirmation() {
//		Long playerId = 33L;
//		Long teamId = 88L;
//		FeeType feeType = FeeType.CREDIT;
//		PayMethod payMethod = PayMethod.BANK;
//		BigDecimal amount = BigDecimal.valueOf(10.1);
//		
//		Long eventId = 91L;
//		Integer delta = 1;
//		
//		FootballPlayerBalance balance = new FootballPlayerBalance();
//		balance.setPlayerId(playerId);
//		balance.setTeamId(teamId);
//		balance.setFeeType(feeType);
//		balance.setPayMethod(payMethod);
//		balance.setAmount(amount);
//		
//		List<FootballPlayerBalance> balances = Lists.newArrayList(balance);
//		
//		
//		FootballPlayerTeamActivity activity = new FootballPlayerTeamActivity();
//		activity.setDelta(delta);
//		activity.setEventId(eventId);
//		activity.setPlayerId(playerId);
//		activity.setTeamId(teamId);
//		activity.setType(PlayerTeamActivityType.ATTEND);
//		
//		List<FootballPlayerTeamActivity> activities = Lists.newArrayList(activity);
//		
//		try {
//			playerManager.processEventConfirmation(eventId, balances, activities);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException();
//		}
		
	}

}
