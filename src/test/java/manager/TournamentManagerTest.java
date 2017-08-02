package manager;

import java.util.Date;

import org.junit.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.footballer.rest.api.v2.manager.TournamentManager;
import com.footballer.rest.api.v2.vo.Tournament;
import com.footballer.rest.api.v2.vo.Video;
import com.footballer.rest.api.v2.vo.enumType.TournamentType;
import com.footballer.rest.api.v2.vo.enumType.VideoType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"/applicationContext.xml", 
		"/applicationContext-injectBeans.xml", 
		"/applicationContext-redis.xml"})
public class TournamentManagerTest {
	
	@Autowired
	private TournamentManager tournamentManager;

	@Test
	@Ignore
	public void testSaveVideo() {
		Video video = new Video();
		video.setType(VideoType.HIGHLIGHTS);
		video.setTournamentId(1L);
		video.setTournamentMatchId(1L);
		video.setName("test");
		String date = "2016-03-21 19:30:00";
		video.setDisplayDate(date);
		video.setUrl("http://v.qq.com/x/page/z0189ulrjqj.html");
		video.setThumbnail("https://r1.ykimg.com/0542040856FCCA176A0A4504508AA9B3");		
		
		int count = tournamentManager.insertVideo(video);
		System.out.println(count);
	}
	
	@Test
	public void testSaveTournament() {
		Tournament t = new Tournament();
		Date date = new Date();
		t.setName("test");
		t.setType(TournamentType.LEAGUE);
		t.setStartDt(date);
		t.setEndDt(date);
		t.setLogo("/logo");
		t.setAddress("address");
		t.setMainOrganiserName("mainOrganiserName");
		t.setMainOrganiserLogo("/mainOrganiserLogo");
		t.setRegisterStartDateTime(date);
		t.setRegisterEndDateTime(date);
		t.setCreateBy("createby");
		t.setCreateDt(date);
		t.setArenaId(1L);
		
		int count = tournamentManager.saveTournament(t);
		Assert.assertEquals(1, count);
	}
}
