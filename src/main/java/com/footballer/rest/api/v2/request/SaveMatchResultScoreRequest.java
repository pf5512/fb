package com.footballer.rest.api.v2.request;

import java.util.List;

import com.footballer.rest.api.v2.vo.TeamMatchResult;

public class SaveMatchResultScoreRequest {
	
	private List<TeamMatchResult> list;
	private Long tournamentMatchId;
	
	public List<TeamMatchResult> getList() {
		return list;
	}
	public void setList(List<TeamMatchResult> list) {
		this.list = list;
	}
	public Long getTournamentMatchId() {
		return tournamentMatchId;
	}
	public void setTournamentMatchId(Long tournamentMatchId) {
		this.tournamentMatchId = tournamentMatchId;
	}

}
