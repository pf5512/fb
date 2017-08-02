package com.footballer.rest.api.v1.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.footballer.rest.api.v1.vo.base.GenericVo;
import com.footballer.rest.api.v1.vo.enumType.PlayerTeamActivityType;

@Entity
@Table(name = "player_team_activity")
public class PlayerTeamActivity extends GenericVo {
	
	private Long playerId;
	private Long teamId;
	private Long eventId;
	
	private PlayerTeamActivityType type;

	@Column(name = "event_id", nullable = false)
	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	@Column(name = "type", nullable = false)
	@Enumerated(value=EnumType.STRING)
	public PlayerTeamActivityType getType() {
		return type;
	}

	public void setType(PlayerTeamActivityType type) {
		this.type = type;
	}
	
	@Column(name = "player_id", nullable = false)
	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	@Column(name = "team_id", nullable = true)
	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

}
