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
import com.footballer.rest.api.v1.vo.enumType.InvitationType;


@SuppressWarnings("serial")
@Entity
@Table(name="team_player_invitation")
public class TeamPlayerInvitation extends GenericVo implements Serializable {
	
	private Long teamId;
	private Long playerId;
	private InvitationResponseType status;
	private Date requestTime;
	private Date responseTime;
	private InvitationType type;
	
	@Column(name = "team_id", nullable = true)
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	
	@Column(name = "player_id", nullable = false)
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	
	@Enumerated(value=EnumType.STRING)
	@Column(name = "status", nullable = false)
	public InvitationResponseType getStatus() {
		return status;
	}
	public void setStatus(InvitationResponseType status) {
		this.status = status;
	}
	
	@Column(name = "request_time", nullable = true)
    @XmlElement(name = "requestTime")
    @XmlJavaTypeAdapter(LongDateAdapter.class)
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	
	@Column(name = "response_time", nullable = true)
    @XmlElement(name = "responseTime")
    @XmlJavaTypeAdapter(LongDateAdapter.class)
	public Date getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}
	
	@Enumerated(value=EnumType.STRING)
	public InvitationType getType() {
		return type;
	}
	public void setType(InvitationType type) {
		this.type = type;
	}
	
}
