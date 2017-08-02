package com.footballer.rest.api.v1.vo;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

import com.footballer.rest.api.v1.vo.base.GenericVo;

@Entity
@Table(name="player_deposit_account")
public class PlayerDepositAccount extends GenericVo{
	private Long playerId;
	private Long teamId;
	private BigDecimal balances;
	
	private List<PlayerBalanceLine> playerBalanceLines; 
	
	@Column(name = "player_id", nullable = false)
    @XmlElement(name = "playerId")
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	
	@Column(name = "team_id", nullable = false)
    @XmlElement(name = "teamId")
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	
	@Column(name = "balances", nullable = false)
    @XmlElement(name = "balances")
	public BigDecimal getBalances() {
		return balances;
	}
	public void setBalances(BigDecimal balances) {
		this.balances = balances;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	public List<PlayerBalanceLine> getPlayerBalanceLines() {
		return playerBalanceLines;
	}
	public void setPlayerBalanceLines(List<PlayerBalanceLine> playerBalanceLines) {
		this.playerBalanceLines = playerBalanceLines;
	}
}
