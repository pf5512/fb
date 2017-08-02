package com.footballer.rest.api.v2.request;

public class SoloRequest {

	private Long soloWishId;
	private Long arenaId;
	private String date;
	private Long playerId;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public Long getArenaId() {
		return arenaId;
	}
	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
	}
	public Long getSoloWishId() {
		return soloWishId;
	}
	public void setSoloWishId(Long soloWishId) {
		this.soloWishId = soloWishId;
	}
	
}
