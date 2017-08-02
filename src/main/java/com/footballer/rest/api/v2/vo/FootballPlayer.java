package com.footballer.rest.api.v2.vo;

import com.footballer.rest.api.v2.vo.base.GenericVo;

public class FootballPlayer  extends GenericVo{

    private String nickName;
    private String imageUrl;
    //private Long arenaId;
    private Long addressId;
    private String deviceToken;
    
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
//	public Long getArenaId() {
//		return arenaId;
//	}
//	public void setArenaId(Long arenaId) {
//		this.arenaId = arenaId;
//	}
  
}
