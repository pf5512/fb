package com.footballer.rest.api.v2.vo;

import com.footballer.rest.api.v2.vo.base.GenericVo;

public class BasketballPlayer  extends GenericVo{

    private String nickName;
    private String imageUrl;
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
  
}
