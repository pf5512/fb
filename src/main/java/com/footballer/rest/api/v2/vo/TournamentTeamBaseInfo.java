package com.footballer.rest.api.v2.vo;

public class TournamentTeamBaseInfo {

	private Long tournamentId;
	private Long teamId;
	private String groupName;
	private Integer groupNumber;
	private String teamName;
	private String teamLogo;
	private Integer playerCount;
	private String teamNation;
	private String teamTech;
	
	private Long captainPlayerId;
	private String captainName;
	private String captainLogo;
	private String createDt;
	
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(Integer groupNumber) {
		this.groupNumber = groupNumber;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getTeamLogo() {
		return teamLogo;
	}
	public void setTeamLogo(String teamLogo) {
		this.teamLogo = teamLogo;
	}
	public Integer getPlayerCount() {
		return playerCount;
	}
	public void setPlayerCount(Integer playerCount) {
		this.playerCount = playerCount;
	}
	public Long getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}
	public String getTeamNation() {
		return teamNation;
	}
	public void setTeamNation(String teamNation) {
		this.teamNation = teamNation;
	}
	public Long getCaptainPlayerId() {
		return captainPlayerId;
	}
	public void setCaptainPlayerId(Long captainPlayerId) {
		this.captainPlayerId = captainPlayerId;
	}
	public String getCaptainName() {
		return captainName;
	}
	public void setCaptainName(String captainName) {
		this.captainName = captainName;
	}
	public String getCaptainLogo() {
		return captainLogo;
	}
	public void setCaptainLogo(String captainLogo) {
		this.captainLogo = captainLogo;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}
	public String getTeamTech() {
		return teamTech;
	}
	public void setTeamTech(String teamTech) {
		this.teamTech = teamTech;
	}
	
}
