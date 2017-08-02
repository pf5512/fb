package com.footballer.rest.api.v1.vo.enumType;

public enum FeeType {
	
	DEBIT("存入"),
	CREDIT("扣除");
	
	private String type;
	
	private FeeType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
}
