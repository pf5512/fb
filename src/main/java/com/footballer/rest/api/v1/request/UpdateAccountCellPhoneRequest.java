package com.footballer.rest.api.v1.request;

public class UpdateAccountCellPhoneRequest {
	
	private String phone;
	private String openid;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
