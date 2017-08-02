package com.footballer.rest.api.v2.request;

public class GetTournamentVideoListRequest {
	
	private Long tournamentId;
	private Long teamId;

	public Long getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

}
