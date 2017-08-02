package com.footballer.rest.api.v1.response;

import java.util.List;

import com.footballer.rest.api.v1.domain.FootballTeam;
import com.footballer.rest.api.v1.domain.PlayerBaseInfo;

public class FindPlayerTeamAllPlayerAndFriendsResponse extends JsonResponse {
	
	List<FootballTeam> teams;
	List<PlayerBaseInfo> friends;
	
	public List<FootballTeam> getTeams() {
		return teams;
	}
	public void setTeams(List<FootballTeam> teams) {
		this.teams = teams;
	}
	public List<PlayerBaseInfo> getFriends() {
		return friends;
	}
	public void setFriends(List<PlayerBaseInfo> friends) {
		this.friends = friends;
	}
	
	

}
