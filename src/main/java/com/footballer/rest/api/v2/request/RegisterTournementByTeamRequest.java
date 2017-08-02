package com.footballer.rest.api.v2.request;

import com.footballer.rest.api.v2.vo.Team;

public class RegisterTournementByTeamRequest {
	
	private Team team;
	
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}

}
