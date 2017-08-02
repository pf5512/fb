package daoTest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.footballer.rest.api.v1.dao.PlayerDao;
import com.footballer.rest.api.v1.domain.PlayerSkillPropertyRanking;
import com.footballer.rest.api.v1.domain.PlayerSkillPropertyTeamRanking;
import com.footballer.rest.api.v1.exception.CopyException;
import com.footballer.util.JacksonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"/applicationContext.xml", 
		"/applicationContext-injectBeans.xml", 
		"/applicationContext-redis.xml"})
public class TestPlayerDao {

	
	@Autowired
	public PlayerDao playerDao;
//	@Test
	public void testFriendsCount() throws CopyException{
		List<PlayerSkillPropertyRanking> list = playerDao.getPlayerFriendsRankingOfSkillType_v2("MVP", 76L);
		List<PlayerSkillPropertyRanking> list2 = playerDao.getPlayerFriendsRankingOfSkillType("MVP", 76L);
		String json = JacksonUtil.toJson(list);
		String json2 = JacksonUtil.toJson(list2);
		if(!json.equals(json2)){
			System.out.println(json);
			System.out.println(json2);
		}
	}
	
	@Test
	public void testTeamCount() throws CopyException{
		List<PlayerSkillPropertyTeamRanking> list = playerDao.getPlayerTeamsRankingOfSkillType_v2("MVP", 76L);
		List<PlayerSkillPropertyTeamRanking> list2 = playerDao.getPlayerTeamsRankingOfSkillType("MVP", 76L);
		String json = JacksonUtil.toJson(list);
		String json2 = JacksonUtil.toJson(list2);
		if(!json.equals(json2)){
			System.out.println(json);
			System.out.println(json2);
		}
	}
	
	
}
