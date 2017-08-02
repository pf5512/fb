package com.footballer.rest.api.v1.result;

import com.footballer.rest.api.v1.vo.Arena;
import com.footballer.rest.api.v1.vo.Event;
import com.footballer.rest.api.v1.vo.Field;
import com.footballer.rest.api.v1.vo.enumType.EventStatus;
import com.footballer.rest.api.v1.vo.enumType.InvitationResponseType;

public class EventResult {
	private OwnerResult owner;
	private Event event;
	private Arena arena;
	private Field field;
	private String fieldAddress;
	private AddressResult address;
	private InvitationResponseType currentUserStatus;
	private Integer joinedPlayerCount;
	private Integer totalPlayerCount;
	private Integer joinedPlayerSignedCount;
	private EventStatus status;
	
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public Arena getArena() {
		return arena;
	}
	public void setArena(Arena arena) {
		this.arena = arena;
	}
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public String getFieldAddress() {
		return fieldAddress;
	}
	public void setFieldAddress(String fieldAddress) {
		this.fieldAddress = fieldAddress;
	}
	public AddressResult getAddress() {
		return address;
	}
	public void setAddress(AddressResult address) {
		this.address = address;
	}
	public OwnerResult getOwner() {
		return owner;
	}
	public void setOwner(OwnerResult owner) {
		this.owner = owner;
	}
	public InvitationResponseType getCurrentUserStatus() {
		return currentUserStatus;
	}
	public void setCurrentUserStatus(InvitationResponseType currentUserStatus) {
		this.currentUserStatus = currentUserStatus;
	}
	public Integer getJoinedPlayerCount() {
		return joinedPlayerCount;
	}
	public void setJoinedPlayerCount(Integer joinedPlayerCount) {
		this.joinedPlayerCount = joinedPlayerCount;
	}
	public Integer getTotalPlayerCount() {
		return totalPlayerCount;
	}
	public void setTotalPlayerCount(Integer totalPlayerCount) {
		this.totalPlayerCount = totalPlayerCount;
	}
	public Integer getJoinedPlayerSignedCount() {
		return joinedPlayerSignedCount;
	}
	public void setJoinedPlayerSignedCount(Integer joinedPlayerSignedCount) {
		this.joinedPlayerSignedCount = joinedPlayerSignedCount;
	}
	public EventStatus getStatus() {
		return status;
	}
	public void setStatus(EventStatus status) {
		this.status = status;
	}
	
	
}
