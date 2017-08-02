package com.footballer.rest.api.v1.request;

public class FootballGamePlayerCommentRequest {
	
	private Long eventId;
	private Long playerId;
	private String comments;
	
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}

}
