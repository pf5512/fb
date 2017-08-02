package com.footballer.rest.api.v2.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class BasketBallTeamMatchResult {

	private Long tournamentMatchId;
	private Long teamId;
	private String teamName;
	private String teamAbbr;
	private String teamLogo;
	private Integer matches;

	@JsonProperty("PTSF")
	private Integer PTSF;
	@JsonProperty("PTSA")
	private Integer PTSA;
	@JsonProperty("PTSD")
	private Integer PTSD;
	
	private Integer P2A;
	private Integer P2M;
	private Integer P3A;
	private Integer P3M;
	
	@JsonProperty("FTA")
	private Integer FTA;
	
	@JsonProperty("FTM")
	private Integer FTM;
	
	@JsonProperty("PF")
	private Integer PF;
	
	@JsonProperty("REB")
	private Integer REB;
	
	@JsonProperty("STL")
	private Integer STL;
	
	@JsonProperty("BLK")
	private Integer BLK;
	
	@JsonProperty("AST")
	private Integer AST;
	
	@JsonProperty("TOV")
	private Integer TOV;

	private Integer playerId;
	private String matchName;
	private Date matchTime;
	
	private Integer leaguePoints;
	private Integer wins;
	private Integer losses;
	
	public Long getTournamentMatchId() {
		return tournamentMatchId;
	}
	public void setTournamentMatchId(Long tournamentMatchId) {
		this.tournamentMatchId = tournamentMatchId;
	}
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getTeamAbbr() {
		return teamAbbr;
	}
	public void setTeamAbbr(String teamAbbr) {
		this.teamAbbr = teamAbbr;
	}
	public String getTeamLogo() {
		return teamLogo;
	}
	public void setTeamLogo(String teamLogo) {
		this.teamLogo = teamLogo;
	}
	public Integer getMatches() {
		return matches;
	}
	public void setMatches(Integer matches) {
		this.matches = matches;
	}
	public Integer getPTSF() {
		return PTSF;
	}
	public void setPTSF(Integer pTSF) {
		PTSF = pTSF;
	}
	public Integer getPTSA() {
		return PTSA;
	}
	public void setPTSA(Integer pTSA) {
		PTSA = pTSA;
	}
	public Integer getPTSD() {
		return PTSD;
	}
	public void setPTSD(Integer pTSD) {
		PTSD = pTSD;
	}
	public Integer getP2A() {
		return P2A;
	}
	public void setP2A(Integer p2a) {
		P2A = p2a;
	}
	public Integer getP2M() {
		return P2M;
	}
	public void setP2M(Integer p2m) {
		P2M = p2m;
	}
	public Integer getP3A() {
		return P3A;
	}
	public void setP3A(Integer p3a) {
		P3A = p3a;
	}
	public Integer getP3M() {
		return P3M;
	}
	public void setP3M(Integer p3m) {
		P3M = p3m;
	}
	public Integer getFTA() {
		return FTA;
	}
	public void setFTA(Integer fTA) {
		FTA = fTA;
	}
	public Integer getFTM() {
		return FTM;
	}
	public void setFTM(Integer fTM) {
		FTM = fTM;
	}
	public Integer getPF() {
		return PF;
	}
	public void setPF(Integer pF) {
		PF = pF;
	}
	public Integer getREB() {
		return REB;
	}
	public void setREB(Integer rEB) {
		REB = rEB;
	}
	public Integer getSTL() {
		return STL;
	}
	public void setSTL(Integer sTL) {
		STL = sTL;
	}
	public Integer getBLK() {
		return BLK;
	}
	public void setBLK(Integer bLK) {
		BLK = bLK;
	}
	public Integer getAST() {
		return AST;
	}
	public void setAST(Integer aST) {
		AST = aST;
	}
	public Integer getTOV() {
		return TOV;
	}
	public void setTOV(Integer tOV) {
		TOV = tOV;
	}
	public Integer getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}
	public String getMatchName() {
		return matchName;
	}
	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}
	public Date getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}
	public Integer getLeaguePoints() {
		return leaguePoints;
	}
	public void setLeaguePoints(Integer leaguePoints) {
		this.leaguePoints = leaguePoints;
	}
	public Integer getWins() {
		return wins;
	}
	public void setWins(Integer wins) {
		this.wins = wins;
	}
	public Integer getLosses() {
		return losses;
	}
	public void setLosses(Integer losses) {
		this.losses = losses;
	}

}
