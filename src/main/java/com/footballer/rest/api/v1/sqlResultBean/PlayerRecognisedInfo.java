package com.footballer.rest.api.v1.sqlResultBean;

import java.util.Date;

public class PlayerRecognisedInfo {

	private String skillType;
	private Long playerId;
	private String playerName;
	private String playerImgUrl;
	private Date recogniseDate;
	
	public String getSkillType() {
		return skillType;
	}
	public void setSkillType(String skillType) {
		this.skillType = skillType;
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
	public String getPlayerImgUrl() {
		return playerImgUrl;
	}
	public void setPlayerImgUrl(String playerImgUrl) {
		this.playerImgUrl = playerImgUrl;
	}
	public Date getRecogniseDate() {
		return recogniseDate;
	}
	public void setRecogniseDate(Date recogniseDate) {
		this.recogniseDate = recogniseDate;
	}
}
