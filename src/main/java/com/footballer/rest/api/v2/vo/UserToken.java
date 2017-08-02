package com.footballer.rest.api.v2.vo;

import java.io.Serializable;
import java.sql.Date;

public class UserToken implements Serializable{
	
	private static final long serialVersionUID = 8763618509755980553L;
	private Long id;
	private String identify;
	private Date expireDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	
}
