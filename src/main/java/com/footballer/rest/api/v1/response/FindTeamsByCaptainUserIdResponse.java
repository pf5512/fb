package com.footballer.rest.api.v1.response;

import java.util.List;

import com.footballer.rest.api.v1.vo.Team;

public class FindTeamsByCaptainUserIdResponse extends JsonResponse {
	
	private List<Team> teams;

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
	

}
