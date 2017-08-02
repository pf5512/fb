package com.footballer.rest.api.v2.domain;

import java.util.Date;

import com.footballer.rest.api.v1.vo.enumType.PlayerPosition;
import com.footballer.rest.api.v1.vo.enumType.TeamTactics;

public class PlayerWithTeamsInfo {

	private Long playerId;
	private String playerName;
	private String playerLogo;
	
	private Date birth;
	private Integer height;
	private Integer weight;

	private Long teamId;
	private String teamStatus;
	private String teamNumber;
	private PlayerPosition teamPosition;
	private String teamName;
	private String teamAbbr;
	private String teamLogo;
	private TeamTactics teamTech;
	private Date teamCreateDt;
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getPlayerLogo() {
		return playerLogo;
	}
	public void setPlayerLogo(String playerLogo) {
		this.playerLogo = playerLogo;
	}
	public String getTeamStatus() {
		return teamStatus;
	}
	public void setTeamStatus(String teamStatus) {
		this.teamStatus = teamStatus;
	}
	public String getTeamNumber() {
		return teamNumber;
	}
	public void setTeamNumber(String teamNumber) {
		this.teamNumber = teamNumber;
	}
	public PlayerPosition getTeamPosition() {
		return teamPosition;
	}
	public void setTeamPosition(PlayerPosition teamPosition) {
		this.teamPosition = teamPosition;
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
	public TeamTactics getTeamTech() {
		return teamTech;
	}
	public void setTeamTech(TeamTactics teamTech) {
		this.teamTech = teamTech;
	}
	public Date getTeamCreateDt() {
		return teamCreateDt;
	}
	public void setTeamCreateDt(Date teamCreateDt) {
		this.teamCreateDt = teamCreateDt;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	} 
}
