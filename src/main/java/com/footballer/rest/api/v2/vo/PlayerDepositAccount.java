package com.footballer.rest.api.v2.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.footballer.rest.api.v1.vo.base.GenericVo;

public class PlayerDepositAccount extends GenericVo implements Serializable{
    
	private static final long serialVersionUID = 1L;
	
	private Long teamId;
    private Long playerId;
    private BigDecimal balance;
            
    public PlayerDepositAccount() {
    }

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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
   
}
