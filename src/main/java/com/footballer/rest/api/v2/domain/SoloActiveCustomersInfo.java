package com.footballer.rest.api.v2.domain;

public class SoloActiveCustomersInfo {
	
	private Long playerId;
	private String playerName;
	private String playerHeadIcon;
	private Long cellphone;
	private Integer useTimes;
	private Integer absentTimes;
	
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
	public String getPlayerHeadIcon() {
		return playerHeadIcon;
	}
	public void setPlayerHeadIcon(String playerHeadIcon) {
		this.playerHeadIcon = playerHeadIcon;
	}
	public Long getCellphone() {
		return cellphone;
	}
	public void setCellphone(Long cellphone) {
		this.cellphone = cellphone;
	}
	public Integer getUseTimes() {
		return useTimes;
	}
	public void setUseTimes(Integer useTimes) {
		this.useTimes = useTimes;
	}
	public Integer getAbsentTimes() {
		return absentTimes;
	}
	public void setAbsentTimes(Integer absentTimes) {
		this.absentTimes = absentTimes;
	}
	
}
