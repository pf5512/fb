package com.footballer.rest.api.v1.vo.enumType;

public enum PayMethod {
	
	PRESTORE("预存金额"),
	ZHIFUBAO("支付宝"),
	BANK("银行转账"),
	CACHE("现金");
	
	private String type;
	
	private PayMethod(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
}
