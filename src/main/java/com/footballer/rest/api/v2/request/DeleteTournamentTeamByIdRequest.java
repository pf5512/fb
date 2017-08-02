package com.footballer.rest.api.v2.request;

import java.util.List;

public class DeleteTournamentTeamByIdRequest {
	
	private Long tournamentId;
	
	private List<Long> teamIdList;

	public Long getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}

	public List<Long> getTeamIdList() {
		return teamIdList;
	}

	public void setTeamIdList(List<Long> teamIdList) {
		this.teamIdList = teamIdList;
	}

}
