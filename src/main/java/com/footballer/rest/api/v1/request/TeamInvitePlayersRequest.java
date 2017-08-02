package com.footballer.rest.api.v1.request;

import java.util.List;

public class TeamInvitePlayersRequest {
	
	private Long teamId;
	private List<Long> playerIds;
	
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	public List<Long> getPlayerIds() {
		return playerIds;
	}
	public void setPlayerIds(List<Long> playerIds) {
		this.playerIds = playerIds;
	}
}