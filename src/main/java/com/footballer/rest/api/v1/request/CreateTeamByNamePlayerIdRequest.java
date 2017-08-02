package com.footballer.rest.api.v1.request;

public class CreateTeamByNamePlayerIdRequest {
	
	private String teamName;
	private Long playerId;
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	
	
}
