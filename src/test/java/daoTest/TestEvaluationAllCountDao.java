package daoTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.footballer.rest.api.v1.dao.EvaluationAllCountDao;
import com.footballer.rest.api.v1.vo.PlayerMutualEvaluationAllCount;
import com.footballer.rest.api.v1.vo.enumType.PlayerSkills;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"/applicationContext.xml", 
		"/applicationContext-injectBeans.xml", 
		"/applicationContext-redis.xml"})
public class TestEvaluationAllCountDao {
	
	@Autowired
	public EvaluationAllCountDao evaluationAllCountDao;
	
	@Test
	public void test(){
//		PlayerMutualEvaluationAllCount pmeac = new PlayerMutualEvaluationAllCount();
//		pmeac.setPlayerId(102l);
//		pmeac.setPlayerSkillType(PlayerSkills.BestAttacker);
//		pmeac.setNumber(0);
//		evaluationAllCountDao.savePlayerMutualEvaluationAllCount(pmeac);
//		evaluationAllCountDao.increaseNumber(102L, PlayerSkills.BestAttacker.name());
//		evaluationAllCountDao.decreaseNumber(102L, PlayerSkills.BestAttacker.name());
	}
	
//	@Test
	public void testGetOne(){
		System.out.println(evaluationAllCountDao.findPlayerOneSkillRecognisedTotalNumber( PlayerSkills.BestAttacker.name(),102l));
	}

}
