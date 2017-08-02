package com.footballer.rest.api.v1.domain;

public class PlayerSkillPropertyRanking {

	private Integer ranking;
	private Long playerId;
	private String playerIconUrl;
	private String playerName;
	private Integer totalNumber;
	private Integer cityRanking;
	private Integer provinceRanking;
	private Integer nationRanking;
	
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public String getPlayerIconUrl() {
		return playerIconUrl;
	}
	public void setPlayerIconUrl(String playerIconUrl) {
		this.playerIconUrl = playerIconUrl;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public Integer getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}
	public Integer getCityRanking() {
		return cityRanking;
	}
	public void setCityRanking(Integer cityRanking) {
		this.cityRanking = cityRanking;
	}
	public Integer getProvinceRanking() {
		return provinceRanking;
	}
	public void setProvinceRanking(Integer provinceRanking) {
		this.provinceRanking = provinceRanking;
	}
	public Integer getNationRanking() {
		return nationRanking;
	}
	public void setNationRanking(Integer nationRanking) {
		this.nationRanking = nationRanking;
	}
	
}
