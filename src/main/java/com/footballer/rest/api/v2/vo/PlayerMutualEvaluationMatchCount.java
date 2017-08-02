package com.footballer.rest.api.v2.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.footballer.rest.api.v1.vo.base.GenericVo;
import com.footballer.rest.api.v1.vo.enumType.PlayerSkills;


@JsonInclude(Include.NON_NULL)
public class PlayerMutualEvaluationMatchCount extends GenericVo {
	
	private Long eventId;
	private Long playerId;
	private PlayerSkills playerSkillType;
	private Integer number;
	private List<Player> recognisedPlayer;
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public PlayerSkills getPlayerSkillType() {
		return playerSkillType;
	}
	public void setPlayerSkillType(PlayerSkills playerSkillType) {
		this.playerSkillType = playerSkillType;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public List<Player> getRecognisedPlayer() {
		return recognisedPlayer;
	}
	public void setRecognisedPlayer(List<Player> recognisedPlayer) {
		this.recognisedPlayer = recognisedPlayer;
	}
	
	
	
}
