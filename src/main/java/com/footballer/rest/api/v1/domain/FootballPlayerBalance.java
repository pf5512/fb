package com.footballer.rest.api.v1.domain;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Required;

import com.footballer.rest.api.v1.vo.enumType.FeeType;
import com.footballer.rest.api.v1.vo.enumType.PayMethod;

public class FootballPlayerBalance {
	
	private Long playerId;
	private Long teamId;
	private Long eventId;
	
	private FeeType feeType;
	private PayMethod payMethod;
	private BigDecimal amount;
	
	private String comment;
	
	@Required
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
	
	public FeeType getFeeType() {
		return feeType;
	}
	public void setFeeType(FeeType feeType) {
		this.feeType = feeType;
	}
	
	public PayMethod getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(PayMethod payMethod) {
		this.payMethod = payMethod;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

}
