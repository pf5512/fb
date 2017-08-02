package com.footballer.rest.api.v2.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.footballer.rest.api.v2.vo.base.GenericVo;
import com.footballer.util.DateUtil;
import com.footballer.rest.api.v1.vo.enumType.FeeType;
import com.footballer.rest.api.v1.vo.enumType.PayMethod;

public class ArenaMemberBalanceLine extends GenericVo {
	
	private Long arenaMemberId;
	private FeeType feeType;
	private PayMethod payMethod;
	private BigDecimal fee;
	private Long eventId;
	private String comment;
	private Long fieldReservationId;
	private String disPlayDate;
	
	public Long getArenaMemberId() {
		return arenaMemberId;
	}
	public void setArenaMemberId(Long arenaMemberId) {
		this.arenaMemberId = arenaMemberId;
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
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getFieldReservationId() {
		return fieldReservationId;
	}
	public void setFieldReservationId(Long fieldReservationId) {
		this.fieldReservationId = fieldReservationId;
	}
	
	public String getDisPlayDate() {
		return DateUtil.formatLongDate(super.getCreateDt());
	}
	
}
