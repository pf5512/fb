package com.footballer.rest.api.v2.domain;

import com.footballer.rest.api.v2.vo.enumType.SupportType;

public class PlayerSupport {
	
	private Long supportId;
	private Long playerId;
	private String playerName;
	private String playerLogo;
	private Boolean status;
	private SupportType type;
	
	
	public Long getSupportId() {
		return supportId;
	}
	public void setSupportId(Long supportId) {
		this.supportId = supportId;
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
	public SupportType getType() {
		return type;
	}
	public void setType(SupportType type) {
		this.type = type;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
}
