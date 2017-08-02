package com.footballer.rest.api.v2.request;

public class RemoveGuestBattle {
	
	public Long getFieldReservationId() {
		return fieldReservationId;
	}
	public void setFieldReservationId(Long fieldReservationId) {
		this.fieldReservationId = fieldReservationId;
	}
	public Long getGuestPlayerId() {
		return guestPlayerId;
	}
	public void setGuestPlayerId(Long guestPlayerId) {
		this.guestPlayerId = guestPlayerId;
	}
	private Long fieldReservationId; 
	private Long guestPlayerId;
	
	
}
