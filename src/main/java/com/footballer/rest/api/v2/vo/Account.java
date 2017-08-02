package com.footballer.rest.api.v2.vo;


public class Account {
	
	private Long id;
	private String name;
	private String password;
	private Long cellphone;
	private String weChatNo;
	private String weChatPicUrl;
	private String weChatOpenId;
	private String status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWeChatNo() {
		return weChatNo;
	}
	public void setWeChatNo(String weChatNo) {
		this.weChatNo = weChatNo;
	}
	public Long getCellphone() {
		return cellphone;
	}
	public void setCellphone(Long cellphone) {
		this.cellphone = cellphone;
	}
	public String getWeChatOpenId() {
		return weChatOpenId;
	}
	public void setWeChatOpenId(String weChatOpenId) {
		this.weChatOpenId = weChatOpenId;
	}
	public String getWeChatPicUrl() {
		return weChatPicUrl;
	}
	public void setWeChatPicUrl(String weChatPicUrl) {
		this.weChatPicUrl = weChatPicUrl;
	}
	
	
}
