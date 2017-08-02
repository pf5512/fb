package com.footballer.rest.api.v2.request;

import java.math.BigDecimal;
import java.util.Date;

public class ArenaMemberRequest {
	
	private Long playerId;
	private Long arenaMemberId;
	private String name;
	private String teamName;
	private Long arenaId;
	private String wechatNo;
	private Long cellphone;
	private BigDecimal price;
	private BigDecimal customPrice;
	private BigDecimal customDiscount;
	private Integer level;
	private String comments;
	
	public Long getArenaMemberId() {
		return arenaMemberId;
	}
	public void setArenaMemberId(Long arenaMemberId) {
		this.arenaMemberId = arenaMemberId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Long getArenaId() {
		return arenaId;
	}
	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
	}
	public String getWechatNo() {
		return wechatNo;
	}
	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}
	public Long getCellphone() {
		return cellphone;
	}
	public void setCellphone(Long cellphone) {
		this.cellphone = cellphone;
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
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public BigDecimal getCustomPrice() {
		return customPrice;
	}
	public void setCustomPrice(BigDecimal customPrice) {
		this.customPrice = customPrice;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public BigDecimal getCustomDiscount() {
		return customDiscount;
	}
	public void setCustomDiscount(BigDecimal customDiscount) {
		this.customDiscount = customDiscount;
	}

}
