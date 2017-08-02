package com.footballer.rest.api.v1.vo.enumType;

public enum EventType {
	footballGame("娱乐约球"),			//娱乐约球
	footballMatch("比赛约球"),	    //比赛约球
	watchFootballMatch("观赛活动");   //观赛活动
	
	private String type;
	
	private EventType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
