package com.footballer.rest.api.v2.vo;

public class ArenaOwner {

	private Long id;
	private Long arenaId;
	private Long playerId;
	private String openId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getArenaId() {
		return arenaId;
	}
	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
