package com.footballer.rest.api.v2.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.footballer.rest.api.v1.vo.base.GenericVo;
import com.footballer.rest.api.v1.vo.enumType.PlayerSkills;


@JsonInclude(Include.NON_NULL)
public class PlayerMutualEvaluationAllCount extends GenericVo {
	
	private Long playerId;
	private PlayerSkills playerSkillType;
	private Integer number;
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
	
	
}
