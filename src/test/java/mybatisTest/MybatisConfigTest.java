package mybatisTest;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.footballer.rest.api.v2.vo.PlayerMutualEvaluation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"/applicationContext.xml", 
		"/applicationContext-injectBeans.xml", 
		"/applicationContext-redis.xml"})
public class MybatisConfigTest {
	
	@Autowired
	private SqlSession sqlSession;

	@Test
	public void testRootConfig() {
		List<PlayerMutualEvaluation> list = sqlSession.selectList("findPlayerMutualEvaluations");
		System.out.println("list size:" + list.size());
	}
}
