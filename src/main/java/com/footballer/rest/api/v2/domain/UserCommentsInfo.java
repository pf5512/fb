package com.footballer.rest.api.v2.domain;

import java.util.Date;

public class UserCommentsInfo {

	private Long commentId;
	private Long playerId;
	private String playerName;
	private String playerLogo;
	private String comments;
	private Date commentsTime;
	
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
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
	public String getPlayerLogo() {
		return playerLogo;
	}
	public void setPlayerLogo(String playerLogo) {
		this.playerLogo = playerLogo;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getCommentsTime() {
		return commentsTime;
	}
	public void setCommentsTime(Date commentsTime) {
		this.commentsTime = commentsTime;
	}
	
	
}
