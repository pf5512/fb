package com.footballer.rest.api.v1.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.footballer.rest.api.v1.adapter.LongDateAdapter;
import com.footballer.rest.api.v1.vo.base.GenericVo;
import com.footballer.rest.api.v1.vo.enumType.InvitationResponseType;
import com.footballer.rest.api.v1.vo.enumType.PlayerSkills;


@SuppressWarnings("serial")
@Entity
@Table(name="player_mutual_evaluation")
public class PlayerMutualEvaluation extends GenericVo implements Serializable {
	
	private Long eventId;
	private Long playerId;
	private Long recognisePlayerId;
	private PlayerSkills playerSkillType;
	
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
	
	@Column(name = "recognise_player_id", nullable = false)
	public Long getRecognisePlayerId() {
		return recognisePlayerId;
	}
	public void setRecognisePlayerId(Long recognisePlayerId) {
		this.recognisePlayerId = recognisePlayerId;
	}
	
	@Enumerated(value=EnumType.STRING)
	@Column(name = "property_type", nullable = false)
	public PlayerSkills getPlayerSkillType() {
		return playerSkillType;
	}
	public void setPlayerSkillType(PlayerSkills playerSkillType) {
		this.playerSkillType = playerSkillType;
	}
	
}
