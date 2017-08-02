package com.footballer.rest.api.v1.request;

public class PlayerCheckInRequest {
	
	private Long eventId;
	private Long playerId;
	private String longitude;
	private String latitude;
	
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerIds(Long playerId) {
		this.playerId = playerId;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	

}
