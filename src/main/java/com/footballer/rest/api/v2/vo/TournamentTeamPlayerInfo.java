package com.footballer.rest.api.v2.vo;

import java.util.Date;

public class TournamentTeamPlayerInfo {

	private Long playerId;
	private String playerName;
	private String playerImg;
	private Integer number;
	private String  position;
	private String status;
	private Integer teamMVP;
	private Integer MVP;
	private String birth;
	private Integer height;
	private Integer weight;
	
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
	public String getPlayerImg() {
		return playerImg;
	}
	public void setPlayerImg(String playerImg) {
		this.playerImg = playerImg;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getTeamMVP() {
		return teamMVP;
	}
	public void setTeamMVP(Integer teamMVP) {
		this.teamMVP = teamMVP;
	}
	public Integer getMVP() {
		return MVP;
	}
	public void setMVP(Integer mVP) {
		MVP = mVP;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	
}
