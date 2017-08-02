package manager;

import java.util.List;

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
import com.footballer.rest.api.v1.vo.Account;
import com.footballer.rest.api.v1.vo.UserToken;
import com.footballer.rest.api.v1.vo.enumType.AppTokenType;
import com.footballer.rest.api.v2.manager.FieldReservationManager;
import com.footballer.rest.api.v2.result.ArenasFieldsReservationResult;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
		"classpath:applicationContext-injectBeans.xml",
		"classpath:applicationContext-redis.xml",
		"classpath:dataSource.xml"})
@TransactionConfiguration(transactionManager = "transactionManager",defaultRollback = true)
@Transactional
public class FieldReservationManagerTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	private static final Long ACCOUNT_ID = 1L;
	
	@Autowired
	private FieldReservationManager manager;
	
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
	public void testGetReservation() {
		Long area = 52L;
		String date = "2016-04-14";
		
		List<ArenasFieldsReservationResult> list = manager.getArenasFieldsReservationsByArenaIdAndUseDate(area, date);
	}

}
