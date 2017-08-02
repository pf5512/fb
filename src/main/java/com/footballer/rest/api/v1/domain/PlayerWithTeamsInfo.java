package com.footballer.rest.api.v1.domain;

import java.util.Date;
import java.util.List;

import com.footballer.rest.api.v1.vo.TeamPlayer;

public class PlayerWithTeamsInfo {

	private Long playerId;
	private String playerName;
	private String playerLogo;
	
	private Date playerBirth;
	private Integer playerHeight;
	private Integer playerWeight;
	
	private List<TeamPlayer> playerTeamsInfo;

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

	public Date getPlayerBirth() {
		return playerBirth;
	}

	public void setPlayerBirth(Date playerBirth) {
		this.playerBirth = playerBirth;
	}

	public Integer getPlayerHeight() {
		return playerHeight;
	}

	public void setPlayerHeight(Integer playerHeight) {
		this.playerHeight = playerHeight;
	}

	public Integer getPlayerWeight() {
		return playerWeight;
	}

	public void setPlayerWeight(Integer playerWeight) {
		this.playerWeight = playerWeight;
	}

	public List<TeamPlayer> getPlayerTeamsInfo() {
		return playerTeamsInfo;
	}

	public void setPlayerTeamsInfo(List<TeamPlayer> playerTeamsInfo) {
		this.playerTeamsInfo = playerTeamsInfo;
	}
	
	
}
