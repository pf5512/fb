package com.footballer.rest.api.v2.request;

public class WeChatUserRequest {
	
	private Long arenaId;
	private String name;
	private String openId;
	private String wechatNo;
	private String weChatPicUrl;
	private Long cellphone;
	
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
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getWeChatPicUrl() {
		return weChatPicUrl;
	}
	public void setWeChatPicUrl(String weChatPicUrl) {
		this.weChatPicUrl = weChatPicUrl;
	}

}
