package com.footballer.rest.api.v1.result;

import java.math.BigDecimal;

import com.footballer.rest.api.v1.vo.enumType.PlayerTeamActivityType;

public class PlayerActiviyWithChargeResult {
	
	private Long playerId;
	private String playerName;
	private String playerImg;
	private PlayerTeamActivityType playerTeamActivityType;
	private BigDecimal chargeFee;
	
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
	public PlayerTeamActivityType getPlayerTeamActivityType() {
		return playerTeamActivityType;
	}
	public void setPlayerTeamActivityType(PlayerTeamActivityType playerTeamActivityType) {
		this.playerTeamActivityType = playerTeamActivityType;
	}
	public BigDecimal getChargeFee() {
		return chargeFee;
	}
	public void setChargeFee(BigDecimal chargeFee) {
		this.chargeFee = chargeFee;
	}
}
