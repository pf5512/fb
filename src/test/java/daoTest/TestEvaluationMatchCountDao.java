package daoTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.footballer.rest.api.v1.dao.EvaluationMatchCountDao;
import com.footballer.rest.api.v1.domain.PlayerRecognisedRecord;
import com.footballer.rest.api.v1.exception.CopyException;
import com.footballer.rest.api.v1.vo.enumType.PlayerSkills;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"/applicationContext.xml", 
		"/applicationContext-injectBeans.xml", 
		"/applicationContext-redis.xml"})
public class TestEvaluationMatchCountDao {
	@Autowired
	public EvaluationMatchCountDao evaluationMatchCountDao;
	
//	@Test
	public void test() throws CopyException{
//		evaluationMatchCountDao.increaseNumber(102l, 229l, PlayerSkills.BestAttacker.name());
		
//		evaluationMatchCountDao.decreaseNumber(102l, 229l, PlayerSkills.BestAttacker.name());
	}
	
	@Test
	public void testGetOne() throws CopyException{
//		evaluationMatchCountDao.increaseNumber(102l, 229l, PlayerSkills.BestAttacker.name());
		
		System.out.println(evaluationMatchCountDao.findPlayerOneSkillRecognisedMatchNumber(PlayerSkills.BestAttacker.name(), 102l, 229l));
	}
	

}
