package com.footballer.rest.api.v2.vo;

import java.math.BigDecimal;

import com.footballer.rest.api.v2.vo.base.GenericVo;

public class MemberShip extends GenericVo {
	
	private Long id;
	private Long playerId;
	private Long arenaMemberDiscountId;
	private String name;
	//private Long cellphone;
	//private String wechatNo;
	private BigDecimal totalBalance;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
//	public String getWechatNo() {
//		return wechatNo;
//	}
//	public void setWechatNo(String wechatNo) {
//		this.wechatNo = wechatNo;
//	}
	public Long getArenaMemberDiscountId() {
		return arenaMemberDiscountId;
	}
	public void setArenaMemberDiscountId(Long arenaMemberDiscountId) {
		this.arenaMemberDiscountId = arenaMemberDiscountId;
	}
	public BigDecimal getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
	}
//	public Long getCellphone() {
//		return cellphone;
//	}
//	public void setCellphone(Long cellphone) {
//		this.cellphone = cellphone;
//	}
}
