package com.footballer.rest.api.v2.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.footballer.rest.api.v1.vo.PlayerProperty;

/**
 * Created by sam on 9/27/15.
 */

@JsonInclude(Include.NON_NULL) 
public class Player {

    private Long id;
    private String nickName;
    private String openId;
    private Long cellPhone;
    private String weChatNo;
    private PlayerProperty playerProperty;
    private String imageUrl;
    private String deviceToken;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public PlayerProperty getPlayerProperty() {
		return playerProperty;
	}
	public void setPlayerProperty(PlayerProperty playerProperty) {
		this.playerProperty = playerProperty;
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
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Long getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(Long cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getWeChatNo() {
		return weChatNo;
	}
	public void setWeChatNo(String weChatNo) {
		this.weChatNo = weChatNo;
	}
  
}
