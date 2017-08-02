package com.footballer.rest.api.v1.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.footballer.rest.api.v1.adapter.LongDateAdapter;
import com.footballer.rest.api.v1.vo.enumType.InvitationResponseType;


@Entity
@Table(name="event_invitation")
public class EventInvitation {
	
	private Long id;
	private Long eventId;
	private Long playerId;
	private InvitationResponseType status;
	private Integer friendsNo;
	private Boolean signed;
	private Boolean admin;
	private Date replyTime;
	private Date signTime;
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
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
	@Column(name = "status", nullable = false)
	public InvitationResponseType getStatus() {
		return status;
	}
	public void setStatus(InvitationResponseType status) {
		this.status = status;
	}
	
	@Column(name = "friend_no", nullable = true)
	public Integer getFriendsNo() {
		return friendsNo;
	}
	public void setFriendsNo(Integer friendsNo) {
		this.friendsNo = friendsNo;
	}
	
	@Column(name = "is_signed", nullable = true)
	public Boolean getSigned() {
		return signed;
	}
	public void setSigned(Boolean signed) {
		this.signed = signed;
	}
	
	@Column(name = "is_admin", nullable = true)
	public Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	
	@Column(name = "reply_time", nullable = true)
    @XmlElement(name = "replyTime")
    @XmlJavaTypeAdapter(LongDateAdapter.class)
	public Date getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	
	@Column(name = "sign_time", nullable = true)
    @XmlElement(name = "signTime")
    @XmlJavaTypeAdapter(LongDateAdapter.class)
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	
	@Override
	public String toString() {
		return "event id:" + this.eventId + ", playerId:" + this.playerId + ", id:" + this.id;
	}
	

	
}
