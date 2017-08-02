package com.footballer.rest.api.v2.request;

import java.math.BigDecimal;

import com.footballer.rest.api.v2.vo.enumType.BookStatus;
import com.footballer.rest.api.v2.vo.enumType.PaymentStatus;

public class FieldReservationRequest {
	
	private Long fieldReservationId;
	private Long arenaId;
	private Long fcsId;
	private Long playerId;
	private Long guestPlayerId;
	private Long memberId;
	private String useDate;
	private BigDecimal price;
	//fullField, battle
	private String type;
	private int videoStatus;
	private Long removePlayerId;
	private BookStatus bookStatus;
	private PaymentStatus paymentStatus;
	
	private Integer dayOfWeek;
	private Long fieldId;
	
	//admin,weChat,app
	private String operateType;
	
	public Long getFcsId() {
		return fcsId;
	}
	public void setFcsId(Long fcsId) {
		this.fcsId = fcsId;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public String getUseDate() {
		return useDate;
	}
	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Long getArenaId() {
		return arenaId;
	}
	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
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
	public int getVideoStatus() {
		return videoStatus;
	}
	public void setVideoStatus(int videoStatus) {
		this.videoStatus = videoStatus;
	}
	public Long getGuestPlayerId() {
		return guestPlayerId;
	}
	public void setGuestPlayerId(Long guestPlayerId) {
		this.guestPlayerId = guestPlayerId;
	}
	public Long getRemovePlayerId() {
		return removePlayerId;
	}
	public void setRemovePlayerId(Long removePlayerId) {
		this.removePlayerId = removePlayerId;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public Long getFieldId() {
		return fieldId;
	}
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	public BookStatus getBookStatus() {
		return bookStatus;
	}
	public void setBookStatus(BookStatus bookStatus) {
		this.bookStatus = bookStatus;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	

}
