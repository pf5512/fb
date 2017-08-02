package com.footballer.rest.api.v2.vo;

import java.util.Date;

import com.footballer.rest.api.v2.vo.enumType.SupportType;

public class Support {

	private Long id;
	private Long playerId;
	private Long videoId;
	private Long commentId;
	private Long momentId;
	private Date date;
	private Boolean status;
	private SupportType type;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public SupportType getType() {
		return type;
	}
	public void setType(SupportType type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Long getMomentId() {
		return momentId;
	}
	public void setMomentId(Long momentId) {
		this.momentId = momentId;
	}
}
