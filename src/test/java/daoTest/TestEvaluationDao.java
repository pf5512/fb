package daoTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.footballer.rest.api.v1.dao.EvaluationDao;
import com.footballer.rest.api.v1.domain.PlayerRecognisedRecord;
import com.footballer.rest.api.v1.exception.CopyException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"/applicationContext.xml", 
		"/applicationContext-injectBeans.xml", 
		"/applicationContext-redis.xml"})
public class TestEvaluationDao {
	@Autowired
	public EvaluationDao evaluationDao;
	
	@Test
	public void test() throws CopyException{
		List<PlayerRecognisedRecord> list = evaluationDao.getPlayerRecognisedRecords_v2(229l, 102l);
		System.out.println(list);
	}

}
