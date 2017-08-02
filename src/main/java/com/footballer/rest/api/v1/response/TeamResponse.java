package com.footballer.rest.api.v1.response;

import java.util.List;

import com.footballer.rest.api.v1.vo.Team;

/**
 * Created by justin on 6/29/14.
 */

public class TeamResponse extends JsonResponse {

	private Team team;
	private List<Team> teamList;

	public TeamResponse() {
		super();
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<Team> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<Team> teamList) {
		this.teamList = teamList;
	}

}
