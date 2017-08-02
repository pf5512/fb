package com.footballer.rest.api.v2.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class BasketBallPlayerMatchResult {
	
	private Long tournamentMatchId;
	private Long playerId;
	private String playerNumber;
	private String playerName;
	private String playerImg;
	private Integer teamNumber;
	private String teamPosition;
	private Long teamId;
	private String teamName;
	private String nameAbbr;
	private String teamLogo;
	private Integer GP; //Game Play

	@JsonProperty("PTS")
	private Integer PTS;
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
	
	private Integer teamMVP;
	private Integer mvp;
	
	private Double PTSG;
	private Double P2T;
	private Double P3T;
	private Double FT;
	private Double PFG;
	private Double REBG;
	private Double STLG;
	private Double BLKG;
	private Double ASTG;
	private Double TOVG;
	
	

	private String matchName;
	private Date matchTime;

	public Long getTournamentMatchId() {
		return tournamentMatchId;
	}

	public void setTournamentMatchId(Long tournamentMatchId) {
		this.tournamentMatchId = tournamentMatchId;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public String getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(String playerNumber) {
		this.playerNumber = playerNumber;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerImg() {
		return playerImg;
	}

	public void setPlayerImg(String playerImg) {
		this.playerImg = playerImg;
	}

	public Integer getTeamNumber() {
		return teamNumber;
	}

	public void setTeamNumber(Integer teamNumber) {
		this.teamNumber = teamNumber;
	}

	public String getTeamPosition() {
		return teamPosition;
	}

	public void setTeamPosition(String teamPosition) {
		this.teamPosition = teamPosition;
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

	public String getNameAbbr() {
		return nameAbbr;
	}

	public void setNameAbbr(String nameAbbr) {
		this.nameAbbr = nameAbbr;
	}

	public String getTeamLogo() {
		return teamLogo;
	}

	public void setTeamLogo(String teamLogo) {
		this.teamLogo = teamLogo;
	}


	public Integer getPTS() {
		return PTS;
	}

	public void setPTS(Integer pTS) {
		PTS = pTS;
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

	public Integer getTeamMVP() {
		return teamMVP;
	}

	public void setTeamMVP(Integer teamMVP) {
		this.teamMVP = teamMVP;
	}

	public Integer getMvp() {
		return mvp;
	}

	public void setMvp(Integer mvp) {
		this.mvp = mvp;
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

	public Integer getGP() {
		return GP;
	}

	public void setGP(Integer gP) {
		GP = gP;
	}

	public Double getPTSG() {
		return PTSG;
	}

	public void setPTSG(Double pTSG) {
		PTSG = pTSG;
	}

	public Double getP2T() {
		return P2T;
	}

	public void setP2T(Double p2t) {
		P2T = p2t;
	}

	public Double getP3T() {
		return P3T;
	}

	public void setP3T(Double p3t) {
		P3T = p3t;
	}

	public Double getFT() {
		return FT;
	}

	public void setFT(Double fT) {
		FT = fT;
	}

	public Double getPFG() {
		return PFG;
	}

	public void setPFG(Double pFG) {
		PFG = pFG;
	}

	public Double getREBG() {
		return REBG;
	}

	public void setREBG(Double rEBG) {
		REBG = rEBG;
	}

	public Double getSTLG() {
		return STLG;
	}

	public void setSTLG(Double sTLG) {
		STLG = sTLG;
	}

	public Double getBLKG() {
		return BLKG;
	}

	public void setBLKG(Double bLKG) {
		BLKG = bLKG;
	}

	public Double getASTG() {
		return ASTG;
	}

	public void setASTG(Double aSTG) {
		ASTG = aSTG;
	}

	public Double getTOVG() {
		return TOVG;
	}

	public void setTOVG(Double tOVG) {
		TOVG = tOVG;
	}

}
