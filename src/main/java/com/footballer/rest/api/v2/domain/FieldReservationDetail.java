package com.footballer.rest.api.v2.domain; 
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.footballer.rest.api.v1.vo.enumType.EventType;
import com.footballer.rest.api.v2.vo.base.GenericVo;
import com.footballer.rest.api.v2.vo.enumType.BookStatus;
import com.footballer.rest.api.v2.vo.enumType.PaymentStatus;

public class FieldReservationDetail{

	private Long arenaId;
	private Long fieldId;
	private String fieldName;
	private Long fieldReservationId;
	private Long fieldChargeStandardId;
	private Long playerId;
	private String playerName;
	private Long memberId;
	private String startTime;
	private String endTime;
	private String disPlayStartTime;
	private String disPlayEndTime;
	private BigDecimal price;
	private BigDecimal barginPrice;
	private String disPlayUseDate;
	private String paymentStatus;
	private String guestPaymentStatus;
	private Long guestPlayerId;
	
	private String bookDate;
	private String bookStatus;
	private String cancelDate;
	private String useDate;
	
	private String type;
	
	
	public Long getArenaId() {
		return arenaId;
	}
	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
	}
	public Long getFieldId() {
		return fieldId;
	}
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getDisPlayStartTime() {
		return disPlayStartTime;
	}
	public void setDisPlayStartTime(String disPlayStartTime) {
		this.disPlayStartTime = disPlayStartTime;
	}
	public String getDisPlayEndTime() {
		return disPlayEndTime;
	}
	public void setDisPlayEndTime(String disPlayEndTime) {
		this.disPlayEndTime = disPlayEndTime;
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
	public String getDisPlayUseDate() {
		return disPlayUseDate;
	}
	public void setDisPlayUseDate(String disPlayUseDate) {
		this.disPlayUseDate = disPlayUseDate;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Long getFieldReservationId() {
		return fieldReservationId;
	}
	public void setFieldReservationId(Long fieldReservationId) {
		this.fieldReservationId = fieldReservationId;
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
	public String getBookDate() {
		return bookDate;
	}
	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}
	public String getBookStatus() {
		return bookStatus;
	}
	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
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
	public Long getFieldChargeStandardId() {
		return fieldChargeStandardId;
	}
	public void setFieldChargeStandardId(Long fieldChargeStandardId) {
		this.fieldChargeStandardId = fieldChargeStandardId;
	}
	public String getGuestPaymentStatus() {
		return guestPaymentStatus;
	}
	public void setGuestPaymentStatus(String guestPaymentStatus) {
		this.guestPaymentStatus = guestPaymentStatus;
	}
	public Long getGuestPlayerId() {
		return guestPlayerId;
	}
	public void setGuestPlayerId(Long guestPlayerId) {
		this.guestPlayerId = guestPlayerId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
