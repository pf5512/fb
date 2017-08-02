package com.footballer.rest.api.v1.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.footballer.rest.api.v1.vo.base.GenericVo;
import com.footballer.rest.api.v1.vo.enumType.PlayerSkills;


@SuppressWarnings("serial")
@Entity
@Table(name="player_mutual_evaluation_match_count")
public class PlayerMutualEvaluationMatchCount extends GenericVo implements Serializable {
	
	private Long eventId;
	private Long playerId;
	private PlayerSkills playerSkillType;
	private Integer number;
	
	@Column(name = "event_id", nullable = false)
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	@Column(name = "player_id", nullable = false)
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	
	
	@Enumerated(value=EnumType.STRING)
	@Column(name = "property_type", nullable = false)
	public PlayerSkills getPlayerSkillType() {
		return playerSkillType;
	}
	public void setPlayerSkillType(PlayerSkills playerSkillType) {
		this.playerSkillType = playerSkillType;
	}

	
	@Column(name = "number", nullable = false)
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
}
