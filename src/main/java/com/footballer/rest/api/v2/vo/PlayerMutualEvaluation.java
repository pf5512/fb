package com.footballer.rest.api.v2.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.footballer.rest.api.v1.vo.enumType.PlayerSkills;
import com.footballer.rest.api.v2.vo.base.GenericVo;


@JsonInclude(Include.NON_NULL) 
public class PlayerMutualEvaluation extends GenericVo {
	
	private Long eventId;
	private Long playerId;
	private Long recognisePlayerId;
	private PlayerSkills propertyType;
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
	public Long getRecognisePlayerId() {
		return recognisePlayerId;
	}
	public void setRecognisePlayerId(Long recognisePlayerId) {
		this.recognisePlayerId = recognisePlayerId;
	}
	public PlayerSkills getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(PlayerSkills propertyType) {
		this.propertyType = propertyType;
	}

	
	

}
