package com.footballer.rest.api.v2.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.footballer.rest.api.v2.vo.enumType.UserType;

public class ArenaUser {

	private Long id;
	private Long arenaId;
	private Long playerId;
	private String name;
	private String teams;
	private BigInteger cellphone;
	private String wechatNo;
	private boolean isMember;
	private UserType userType;
	private String comments;
	private BigDecimal customPrice;
	private Date createDt;
	private Integer totalCount;
	
	public Long getArenaId() {
		return arenaId;
	}
	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
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
	public String getTeams() {
		return teams;
	}
	public void setTeams(String teams) {
		this.teams = teams;
	}
	public BigInteger getCellphone() {
		return cellphone;
	}
	public void setCellphone(BigInteger cellphone) {
		this.cellphone = cellphone;
	}
	public String getWechatNo() {
		return wechatNo;
	}
	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}
	public boolean isMember() {
		return isMember;
	}
	public void setMember(boolean isMember) {
		this.isMember = isMember;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
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
	public Date getCreateDt() {
		return createDt;
	}
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
}
