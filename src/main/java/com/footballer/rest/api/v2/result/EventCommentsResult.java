package com.footballer.rest.api.v2.result;

import java.util.Date;

import com.footballer.util.DateUtil;

public class EventCommentsResult {
	
	private Long playerId;
	private String playerImgUrl;
	private String playerName;
	private String Comments;
	private Date commentTime;
	
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public String getComments() {
		return Comments;
	}
	public void setComments(String comments) {
		Comments = comments;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getCommentTime() {
		return DateUtil.formatLongDate(commentTime);
	}
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
	public String getPlayerImgUrl() {
		return playerImgUrl;
	}
	public void setPlayerImgUrl(String playerImgUrl) {
		this.playerImgUrl = playerImgUrl;
	}
}
