package com.footballer.rest.api.v2.domain;

import java.util.Date;

public class UserShareInfo {
	
	private Long id;
	private Long playerId;
	private String playerName;
	private String playerLogo;
	private Date date;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
