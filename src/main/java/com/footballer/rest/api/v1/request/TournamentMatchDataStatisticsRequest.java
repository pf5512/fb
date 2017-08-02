package com.footballer.rest.api.v1.request;

import java.util.List;

import com.footballer.rest.api.v2.domain.BasketBallMatchResult;
import com.footballer.rest.api.v2.vo.PlayerMatchResult;
import com.footballer.rest.api.v2.vo.TeamMatchResult;

public class TournamentMatchDataStatisticsRequest {

	private Long tournamentId;
	private Long matchId;
	private List<Long> matchIdsList;
	private List<PlayerMatchResult> importPlayerDataList;
	private List<TeamMatchResult> importTeamDataList;
	
	private List<BasketBallMatchResult> importBasketBallMatchResultList;
	
	public Long getMatchId() {
		return matchId;
	}
	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}
	public List<PlayerMatchResult> getImportPlayerDataList() {
		return importPlayerDataList;
	}
	public void setImportPlayerDataList(List<PlayerMatchResult> importPlayerDataList) {
		this.importPlayerDataList = importPlayerDataList;
	}
	public List<TeamMatchResult> getImportTeamDataList() {
		return importTeamDataList;
	}
	public void setImportTeamDataList(List<TeamMatchResult> importTeamDataList) {
		this.importTeamDataList = importTeamDataList;
	}
	public List<BasketBallMatchResult> getImportBasketBallMatchResultList() {
		return importBasketBallMatchResultList;
	}
	public void setImportBasketBallMatchResultList(List<BasketBallMatchResult> importBasketBallMatchResultList) {
		this.importBasketBallMatchResultList = importBasketBallMatchResultList;
	}
	public Long getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}
	public List<Long> getMatchIdsList() {
		return matchIdsList;
	}
	public void setMatchIdsList(List<Long> matchIdsList) {
		this.matchIdsList = matchIdsList;
	}
	
	
}
