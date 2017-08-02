package com.footballer.rest.api.v1.request;

import java.util.List;

import com.footballer.rest.api.v1.vo.Event;

public class CreateEventRequest {
	
	private Event event;
	private List<Long> playerIds;
	private Long eventId;
	
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public List<Long> getPlayerIds() {
		return playerIds;
	}
	public void setPlayerIds(List<Long> playerIds) {
		this.playerIds = playerIds;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	

}
