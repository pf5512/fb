package com.footballer.rest.api.v2.vo.enumType;

public enum UserType {

	BACKEND(0, "后台"), 
	WECHAT(1, "微信"), 
	UNKOWN(10, "未知状态");

	// 成员变量
	private String name;
	private int index;

	// 构造方法
	private UserType(int index, String name) {
		this.index = index;
		this.name = name;
	}

	// 普通方法
	public static UserType getValue(int index) {
		for (UserType t : UserType.values()) {
			if (t.getIndex() == index) {
				return t;
			}
		}
		return null;
	}

	// 普通方法
	public static String getName(int index) {
		for (UserType t : UserType.values()) {
			if (t.getIndex() == index) {
				return t.name;
			}
		}
		return null;
	}

	// get set 方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
