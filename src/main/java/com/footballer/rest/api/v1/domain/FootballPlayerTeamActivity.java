package com.footballer.rest.api.v1.domain;

import com.footballer.rest.api.v1.vo.enumType.PlayerTeamActivityType;

public class FootballPlayerTeamActivity {
	
	private Long playerId;
	private Long teamId;
	private Long eventId;
	private Integer delta;
	
	private PlayerTeamActivityType type;
	
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public Integer getDelta() {
		return delta == null ? Integer.valueOf(0) : delta;
	}
	public void setDelta(Integer delta) {
		this.delta = delta;
	}
	public PlayerTeamActivityType getType() {
		return type;
	}
	public void setType(PlayerTeamActivityType type) {
		this.type = type;
	}

}
