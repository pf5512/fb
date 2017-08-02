package com.footballer.rest.api.v2.request;

import java.math.BigDecimal;

public class ArenaMemberDiscountRequest {
	
	private Long arenaMemberDiscountId;
	private Long arenaId;
	private Integer level;
	private BigDecimal discount;
	private BigDecimal fund;
	
	public Long getArenaId() {
		return arenaId;
	}
	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public BigDecimal getFund() {
		return fund;
	}
	public void setFund(BigDecimal fund) {
		this.fund = fund;
	}
	public Long getArenaMemberDiscountId() {
		return arenaMemberDiscountId;
	}
	public void setArenaMemberDiscountId(Long arenaMemberDiscountId) {
		this.arenaMemberDiscountId = arenaMemberDiscountId;
	}
	
}
