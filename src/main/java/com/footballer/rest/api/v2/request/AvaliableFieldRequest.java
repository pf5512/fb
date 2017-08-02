package com.footballer.rest.api.v2.request;

public class AvaliableFieldRequest {

	private Long arenaId;
	private String startTime;
	private String useDate;
	private Long playerId;
	private Long targetPlayerId;
	
	public Long getArenaId() {
		return arenaId;
	}
	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getUseDate() {
		return useDate;
	}
	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public Long getTargetPlayerId() {
		return targetPlayerId;
	}
	public void setTargetPlayerId(Long targetPlayerId) {
		this.targetPlayerId = targetPlayerId;
	}
	
}
