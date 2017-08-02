package com.footballer.rest.api.v2.request;

import com.footballer.rest.api.v2.vo.enumType.PaymentStatus;

public class battleReservationRequest {
	
	private Long arenaId;
	private Long battleWishId;
	private Long targetBattleWishId;
	private Long fieldId;
	private Long fcsId;
	private Long playerId;
	private Long guestPlayerId;
	private String useDate;
	private PaymentStatus paymentStatus;
	private PaymentStatus guestPaymentStatus;
	
	
	public Long getBattleWishId() {
		return battleWishId;
	}
	public void setBattleWishId(Long battleWishId) {
		this.battleWishId = battleWishId;
	}
	public Long getTargetBattleWishId() {
		return targetBattleWishId;
	}
	public void setTargetBattleWishId(Long targetBattleWishId) {
		this.targetBattleWishId = targetBattleWishId;
	}
	public Long getFieldId() {
		return fieldId;
	}
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
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
	public Long getGuestPlayerId() {
		return guestPlayerId;
	}
	public void setGuestPlayerId(Long guestPlayerId) {
		this.guestPlayerId = guestPlayerId;
	}
	public String getUseDate() {
		return useDate;
	}
	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}
	public Long getArenaId() {
		return arenaId;
	}
	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public PaymentStatus getGuestPaymentStatus() {
		return guestPaymentStatus;
	}
	public void setGuestPaymentStatus(PaymentStatus guestPaymentStatus) {
		this.guestPaymentStatus = guestPaymentStatus;
	}
}
