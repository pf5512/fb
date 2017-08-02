package com.footballer.rest.api.v2.domain;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.footballer.rest.api.v2.vo.enumType.UserType;
import com.footballer.util.ObjectUtil;

public class ArenaMemberInfo {
	
	private Long id;
	private Long arenaId;
	private Long playerId;
	private Long memberId;
	private Integer number;
	private String name;
	private String type;
	private String imageUrl;
	private String teams;
	private Integer level;
	private Double disCount;
	private BigDecimal balance;
	private BigDecimal customDiscount;
	private String wechatNo;
	private BigInteger cellphone;
	private String createDt;
	private Integer fullFieldBookNumber;
	private Integer battleBookNumber;
	private Integer bookTotalNumber;
	private String comments;
	private Integer totalCount;
	private BigDecimal customPrice;
	private String userType;
	private String imgUrl;
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
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
	public Double getDisCount() {
		return disCount;
	}
	public void setDisCount(Double disCount) {
		this.disCount = disCount;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public Long getArenaId() {
		return arenaId;
	}
	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}
	public String getWechatNo() {
		return wechatNo;
	}
	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}
	public BigInteger getCellphone() {
		return cellphone;
	}
	public void setCellphone(BigInteger cellphone) {
		this.cellphone = cellphone;
	}
	public String getTeams() {
		return teams;
	}
	public void setTeams(String teams) {
		this.teams = teams;
	}
	public Integer getFullFieldBookNumber() {
		return fullFieldBookNumber;
	}
	public void setFullFieldBookNumber(Integer fullFieldBookNumber) {
		this.fullFieldBookNumber = fullFieldBookNumber;
	}
	public Integer getBattleBookNumber() {
		return battleBookNumber;
	}
	public void setBattleBookNumber(Integer battleBookNumber) {
		this.battleBookNumber = battleBookNumber;
	}
	public Integer getBookTotalNumber() {
		return bookTotalNumber;
	}
	public void setBookTotalNumber(Integer bookTotalNumber) {
		this.bookTotalNumber = bookTotalNumber;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public BigDecimal getCustomPrice() {
		return customPrice;
	}
	public void setCustomPrice(BigDecimal customPrice) {
		this.customPrice = customPrice;
	}
	public String getUserType() {
		if(ObjectUtil.isNotNull(userType)){
			return UserType.getName(Integer.parseInt(userType));
		}else{
			return userType;
		}
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public BigDecimal getCustomDiscount() {
		return customDiscount;
	}
	public void setCustomDiscount(BigDecimal customDiscount) {
		this.customDiscount = customDiscount;
	}

}
