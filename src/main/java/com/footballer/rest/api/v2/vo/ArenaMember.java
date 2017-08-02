package com.footballer.rest.api.v2.vo;

import java.math.BigDecimal;
import com.footballer.rest.api.v2.vo.base.GenericVo;

public class ArenaMember extends GenericVo {
	
	private Long arenaId;
	private Long playerId;
	private Long memberShipId;
	private Integer memberShipNo;
	private BigDecimal balance;
	private BigDecimal customDiscount;
	private String name;
	private Integer level;
	private String comments;
	
	
	public Long getArenaId() {
		return arenaId;
	}
	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
	}
	public Long getMemberShipId() {
		return memberShipId;
	}
	public void setMemberShipId(Long memberShipId) {
		this.memberShipId = memberShipId;
	}
	public Integer getMemberShipNo() {
		return memberShipNo;
	}
	public void setMemberShipNo(Integer memberShipNo) {
		this.memberShipNo = memberShipNo;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public BigDecimal getCustomDiscount() {
		return customDiscount;
	}
	public void setCustomDiscount(BigDecimal customDiscount) {
		this.customDiscount = customDiscount;
	}
	
}
