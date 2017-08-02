package com.footballer.rest.api.v2.vo;
import java.util.Date;

public class UserShareRecord {

    
	private Long id;
	private Long playerId;
	private Long videoId;
	private Long momentId;
	private String url;
	private String content;
	private Date date;
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getVideoId() {
		return videoId;
	}
	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}
	public Long getMomentId() {
		return momentId;
	}
	public void setMomentId(Long momentId) {
		this.momentId = momentId;
	}
	
}
