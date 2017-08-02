package com.footballer.rest.api.v1.domain;

import java.text.CollationKey;
import java.text.Collator;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.footballer.filter.AppContextHolder;
import com.footballer.rest.api.v1.manager.UserManager;
import com.footballer.rest.api.v1.vo.Account;
import com.footballer.rest.api.v1.vo.Player;
import com.footballer.rest.api.v1.vo.Team;

/**
 * Created by ian on 8/29/14.
 */
public class FootballTeam implements Comparable<FootballTeam>{

    private ClientTeam team;
    private Set<FootballPlayer> footballPlayers;

    public ClientTeam getTeam() {
        return team;
    }

    public void setTeam(ClientTeam team) {
        this.team = team;
    }

    public Set<FootballPlayer> getFootballPlayers() {
        return footballPlayers;
    }

    public void setFootballPlayers(Set<FootballPlayer> footballPlayers) {
        this.footballPlayers = footballPlayers;
    }
    
    public static FootballTeam build(Team team) {
    	// build football team
		FootballTeam footballTeam = new FootballTeam();
        ClientTeam clientTeam = new ClientTeam();
        BeanUtils.copyProperties(team, clientTeam, new String[]{"fields", "players"});
        footballTeam.setTeam(clientTeam);
        
        Set<Player> players = team.getPlayers();

        // Convert player to football players
        if (!CollectionUtils.isEmpty(players)) {
            Set<FootballPlayer> footballPlayers = new HashSet<>();
            Account account = AppContextHolder.getContext().getAccount();

            for (Player player : players) {
                User user = UserManager.buildUser(account, player);
                FootballPlayer footballPlayer = new FootballPlayer();
                footballPlayer.setUser(user);
                footballPlayers.add(footballPlayer);
            }

            footballTeam.setFootballPlayers(footballPlayers);
        }
        
        return footballTeam;
    }

    /**
     * 按照team的名字排序
     */
	@Override
	public int compareTo(FootballTeam o) {
		Collator collator = Collator.getInstance(java.util.Locale.CHINA);
		
		String currentName = team.getName();
		String otherName = o.getTeam().getName();
		
		CollationKey key1 = collator.getCollationKey(currentName);
		CollationKey key2 = collator.getCollationKey(otherName);
		
		return key1.compareTo(key2);
	}
}
