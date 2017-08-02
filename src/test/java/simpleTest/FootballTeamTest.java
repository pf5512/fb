package simpleTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.footballer.rest.api.v1.domain.ClientTeam;
import com.footballer.rest.api.v1.domain.FootballTeam;

public class FootballTeamTest {
	
	@Test
	public void testSortWithChinese() {
		FootballTeam team1 = new FootballTeam();
		ClientTeam clientTeam1 = new ClientTeam();
		clientTeam1.setName("成都游击队");
		team1.setTeam(clientTeam1);
		
		FootballTeam team3 = new FootballTeam();
		ClientTeam clientTeam3 = new ClientTeam();
		clientTeam3.setName("巴萨散打队 --> update");
		team3.setTeam(clientTeam3);
		
		FootballTeam teama = new FootballTeam();
		ClientTeam clientTeama = new ClientTeam();
		clientTeama.setName("a");
		teama.setTeam(clientTeama);
		
		FootballTeam team4 = new FootballTeam();
		ClientTeam clientTeam4 = new ClientTeam();
		clientTeam4.setName("d");
		team4.setTeam(clientTeam4);
		
		FootballTeam team5 = new FootballTeam();
		ClientTeam clientTeam5 = new ClientTeam();
		clientTeam5.setName("y");
		team5.setTeam(clientTeam5);
		
		FootballTeam team2 = new FootballTeam();
		ClientTeam clientTeam2 = new ClientTeam();
		clientTeam2.setName("武汉散打队");
		team2.setTeam(clientTeam2);
		
		List<FootballTeam> list = new ArrayList<>();
		list.add(team2);
		list.add(team1);
		list.add(team3);
		list.add(team4);
		list.add(team5);
		list.add(teama);
		
		Collections.sort(list);
		
		for (FootballTeam t : list) {
			System.out.println(t.getTeam().getName());
		}
		
		Assert.assertEquals(teama.getTeam().getName(), list.get(0).getTeam().getName());
	}

}
