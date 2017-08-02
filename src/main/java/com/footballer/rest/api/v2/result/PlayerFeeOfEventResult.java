package com.footballer.rest.api.v2.result;

import java.math.BigDecimal;

public class PlayerFeeOfEventResult {

	private Long playerID;
	private BigDecimal fee;
	
	public Long getPlayerID() {
		return playerID;
	}
	public void setPlayerID(Long playerID) {
		this.playerID = playerID;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
}
