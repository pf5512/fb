package com.footballer.rest.api.v2.result;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.footballer.rest.api.v1.vo.Arena;
import com.footballer.rest.api.v1.vo.Field;

/**
 * Created by justin on 2015.11.27
 */
public class PlayerFieldReservationResult {
    
	private Long fieldReservationId;
	private String type;
	private Long arenaId;
	private String arenaName;
	private Long fieldId;
	private String fieldName;
	private String startTime;
	private String endTime;
	private String bookDate;
	private String bookStatus;
	private String cancelDate;
	private String useDate;
	private BigDecimal price;
	private BigDecimal weekendPrice;
	private BigDecimal barginPrice;
	private Long memberId;
	private String paymentStatus;
	
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getBarginPrice() {
		return barginPrice;
	}
	public void setBarginPrice(BigDecimal barginPrice) {
		this.barginPrice = barginPrice;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getBookStatus() {
		return bookStatus;
	}
	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Long getFieldId() {
		return fieldId;
	}
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	public Long getArenaId() {
		return arenaId;
	}
	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
	}
	public String getArenaName() {
		return arenaName;
	}
	public void setArenaName(String arenaName) {
		this.arenaName = arenaName;
	}
	public String getBookDate() {
		return bookDate;
	}
	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}
	public String getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}
	public String getUseDate() {
		return useDate;
	}
	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}
	public Long getFieldReservationId() {
		return fieldReservationId;
	}
	public void setFieldReservationId(Long fieldReservationId) {
		this.fieldReservationId = fieldReservationId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getWeekendPrice() {
		return weekendPrice;
	}
	public void setWeekendPrice(BigDecimal weekendPrice) {
		this.weekendPrice = weekendPrice;
	}
	
}
