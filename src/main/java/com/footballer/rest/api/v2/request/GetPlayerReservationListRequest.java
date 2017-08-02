package com.footballer.rest.api.v2.request;

public class GetPlayerReservationListRequest {
	
	private String openId;
	private String arenaMemberId;
	private String playerId;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getArenaMemberId() {
		return arenaMemberId;
	}

	public void setArenaMemberId(String arenaMemberId) {
		this.arenaMemberId = arenaMemberId;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

}
