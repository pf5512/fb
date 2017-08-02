package com.footballer.rest.api.v2.vo;

import java.math.BigDecimal;
import com.footballer.rest.api.v2.vo.base.GenericVo;

public class ArenaMemberDiscount{
	
	private Long arenaId;
	private Integer level;
	private BigDecimal discount;
	private BigDecimal fund;
	private Long id;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
}
