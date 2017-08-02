package redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.service.FakeAccount;
import redis.service.FakeAccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-redis.xml"})
public class SpringRedisTest {
	
	// inject the actual template
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    //@Autowired
    //private RedisCacheManager redisCacheManager;
    
    @Autowired
    private FakeAccountService accountService;
        
    @Test
    public void testRedisTemplate() {
    	Assert.assertNotNull(redisTemplate);
    }
    
    @Test
    public void testRedisCache() {
    	String mapNameSpace = "accountId";
    	String id = "id-1";
    	String value = "1L";
    	
    	redisTemplate.opsForHash().put(mapNameSpace, id, value);
    	
    	Assert.assertEquals(value, redisTemplate.opsForHash().get(mapNameSpace, id));
    }
        
    @Test
    public void testAccounServiceNotNull() {
    	Assert.assertNotNull(accountService);
    }
        
    @Test
    public void testAnnotationCachebale() {
    	System.out.println("111");
    	String identity = "identity-1";
    	
    	FakeAccount account = accountService.findAccountIdByIdentity(identity);
    	Assert.assertEquals(Long.valueOf(2L), account.getAccountId());
    	
    	System.out.println("211");
    	//System.out.println("redisCacheManager:" +  redisCacheManager.getCache("accountCache").get(identity));
    	
    	account = accountService.findAccountIdByIdentity(identity);
    	Assert.assertEquals(Long.valueOf(2L), account.getAccountId());
    	
    	System.out.println("311");
    }
}
