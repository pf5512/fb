package com.footballer.rest.api.v1.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.footballer.rest.api.v1.vo.enumType.PlayerPosition;

public class PlayerTeamSummary {
	
	private Long teamId;
	private Long playerId;
	private Long teamCaptainId;
	private String teamName;
	private String playerName;
	private BigDecimal balances;
	
	private Integer playerNumber;
	private PlayerPosition position;
	private Integer attendTotal;
	private Integer lateTotal;
	private Integer absenceTotal;
	
	private String imageUrl;
	private String teamLogo;
	
	private String playerTeamName;
	
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	@JsonIgnore
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public BigDecimal getBalances() {
		return balances;
	}
	public void setBalances(BigDecimal balances) {
		this.balances = balances;
	}
	public Integer getPlayerNumber() {
		return playerNumber;
	}
	public void setPlayerNumber(Integer playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	@JsonIgnore
	public PlayerPosition getPosition() {
		return position;
	}
	
	public String getPlayerPosition() {
		return null == position ? null : position.getName();
	}
	
	public void setPosition(PlayerPosition position) {
		this.position = position;
	}
	public Integer getAttendTotal() {
		return attendTotal;
	}
	public void setAttendTotal(Integer attendTotal) {
		this.attendTotal = attendTotal;
	}
	public Integer getLateTotal() {
		return lateTotal;
	}
	public void setLateTotal(Integer lateTotal) {
		this.lateTotal = lateTotal;
	}
	public Integer getAbsenceTotal() {
		return absenceTotal;
	}
	public void setAbsenceTotal(Integer absenceTotal) {
		this.absenceTotal = absenceTotal;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getTeamLogo() {
		return teamLogo;
	}
	public void setTeamLogo(String teamLogo) {
		this.teamLogo = teamLogo;
	}
	
	@JsonIgnore
	public String getPlayerTeamName() {
		return playerTeamName;
	}
	public void setPlayerTeamName(String playerTeamName) {
		this.playerTeamName = playerTeamName;
	}
	
	public String getPlayerDisplayName() {
		return null != playerTeamName ? playerTeamName : playerName; 
	}
	public Long getTeamCaptainId() {
		return teamCaptainId;
	}
	public void setTeamCaptainId(Long teamCaptainId) {
		this.teamCaptainId = teamCaptainId;
	}

}
