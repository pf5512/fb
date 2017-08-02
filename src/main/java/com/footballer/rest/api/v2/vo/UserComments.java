package com.footballer.rest.api.v2.vo;
import java.util.Date;

public class UserComments {

    
	private Long id;
	private Long playerId;
	private String comments;
	private Date commentsTime;
	
	private String mobile;
	private String mail;
	private String qq;
	private String wechat;
	
	private Long videoId;
	private Long momentId;

	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public Long getPlayerId(){
		return playerId;
	}

	public void setPlayerId(Long playerId){
		this.playerId = playerId;
	}

	public String getComments(){
		return comments;
	}

	public void setComments(String comments){
		this.comments = comments;
	}

	public Date getCommentsTime(){
		return commentsTime;
	}

	public void setCommentsTime(Date commentsTime){
		this.commentsTime = commentsTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
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
