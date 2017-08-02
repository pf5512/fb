package com.footballer.rest.api.v1.vo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.footballer.rest.api.v1.vo.base.GenericVo;
import com.footballer.rest.api.v1.vo.enumType.FeeType;
import com.footballer.rest.api.v1.vo.enumType.PayMethod;

@Entity
@Table(name="player_balance_lines")
public class PlayerBalanceLine extends GenericVo {
	
	private PlayerDepositAccount account;
	
	private FeeType feeType;
	private PayMethod payMethod;
	private BigDecimal fee;
	private Long eventId;
	
	private String playerName;
	private BigDecimal balances;
	
	private String comment;
		
	@Enumerated(value=EnumType.STRING)
	@Column(name = "fee_type", nullable = false)
    @XmlElement(name = "feeType")
	public FeeType getFeeType() {
		return feeType;
	}
	public void setFeeType(FeeType feeType) {
		this.feeType = feeType;
	}
	
	@Enumerated(value=EnumType.STRING)
	@Column(name = "pay_method", nullable = false)
    @XmlElement(name = "payMethod")
	public PayMethod getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(PayMethod payMethod) {
		this.payMethod = payMethod;
	}
	
	@Column(name = "fee", nullable = false)
    @XmlElement(name = "fee")
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	
	@ManyToOne
	@JoinColumn(name = "player_deposit_account_id")
	@XmlElement(name = "account")
	@JsonIgnore
	public PlayerDepositAccount getAccount() {
		return account;
	}
	public void setAccount(PlayerDepositAccount account) {
		this.account = account;
	}
	
    @Column(name = "comment", nullable = true)
    @XmlElement(name = "comment")
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Column(name = "eventId", nullable = true)
    @XmlElement(name = "eventId")
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	@Transient
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	@Transient
	public BigDecimal getBalances() {
		return balances;
	}
	public void setBalances(BigDecimal balances) {
		this.balances = balances;
	}
}
