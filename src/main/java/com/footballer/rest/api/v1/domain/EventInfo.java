package com.footballer.rest.api.v1.domain;

import java.util.Date;

import com.footballer.rest.api.v1.vo.enumType.EventType;

public class EventInfo {
	private Long id;
 	private Long ownerId;
    private EventType type;
    private Date start_date;
    private Date end_date;
    private Long fieldId;
    private Date replyEndDate;
    private Integer playerCount;
    private Boolean isConfirmed;
    private Integer cost;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public Long getFieldId() {
		return fieldId;
	}
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	public Date getReplyEndDate() {
		return replyEndDate;
	}
	public void setReplyEndDate(Date replyEndDate) {
		this.replyEndDate = replyEndDate;
	}
	public Integer getPlayerCount() {
		return playerCount;
	}
	public void setPlayerCount(Integer playerCount) {
		this.playerCount = playerCount;
	}
	public Boolean getIsConfirmed() {
		return isConfirmed;
	}
	public void setIsConfirmed(Boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
    
}
