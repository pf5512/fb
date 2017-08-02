package com.footballer.rest.api.v2.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.footballer.rest.api.v2.vo.BasketBallPlayerMatchResult;
import com.footballer.rest.api.v2.vo.BasketBallTeamMatchResult;

@JsonInclude(Include.NON_NULL)
public class BasketBallMatchResult {
	
	private Long tournamentMatchId;
	private String strMatchTime;
	private Long teamAId;
	private Long teamBId;
	private String teamAName;
	private String teamBName;

	private List<BasketBallPlayerMatchResult> bbPlayerMatchResultList;
	private List<BasketBallTeamMatchResult> bbTeamMatchResultList;
	
	public Long getTournamentMatchId() {
		return tournamentMatchId;
	}
	public void setTournamentMatchId(Long tournamentMatchId) {
		this.tournamentMatchId = tournamentMatchId;
	}
	public String getStrMatchTime() {
		return strMatchTime;
	}
	public void setStrMatchTime(String strMatchTime) {
		this.strMatchTime = strMatchTime;
	}
	public Long getTeamAId() {
		return teamAId;
	}
	public void setTeamAId(Long teamAId) {
		this.teamAId = teamAId;
	}
	public String getTeamAName() {
		return teamAName;
	}
	public void setTeamAName(String teamAName) {
		this.teamAName = teamAName;
	}
	public Long getTeamBId() {
		return teamBId;
	}
	public void setTeamBId(Long teamBId) {
		this.teamBId = teamBId;
	}
	public String getTeamBName() {
		return teamBName;
	}
	public void setTeamBName(String teamBName) {
		this.teamBName = teamBName;
	}
	public List<BasketBallPlayerMatchResult> getBbPlayerMatchResultList() {
		return bbPlayerMatchResultList;
	}
	public void setBbPlayerMatchResultList(List<BasketBallPlayerMatchResult> bbPlayerMatchResultList) {
		this.bbPlayerMatchResultList = bbPlayerMatchResultList;
	}
	public List<BasketBallTeamMatchResult> getBbTeamMatchResultList() {
		return bbTeamMatchResultList;
	}
	public void setBbTeamMatchResultList(List<BasketBallTeamMatchResult> bbTeamMatchResultList) {
		this.bbTeamMatchResultList = bbTeamMatchResultList;
	}
}
