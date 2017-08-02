package com.footballer.rest.api.v2.request;

public class SupprotRequest {

	private Long supportId;
	private Long playerId;
	private Long videoId;
	private Long momentId;
	private Long commentId;

	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public Long getVideoId() {
		return videoId;
	}
	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public Long getSupportId() {
		return supportId;
	}
	public void setSupportId(Long supportId) {
		this.supportId = supportId;
	}
	public Long getMomentId() {
		return momentId;
	}
	public void setMomentId(Long momentId) {
		this.momentId = momentId;
	}
}
