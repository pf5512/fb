package com.footballer.rest.api.v1.request;

public class FindTeamsByCaptainUserIdRequest {
	
	private Long captainUserId;

	public Long getCaptainUserId() {
		return captainUserId;
	}

	public void setCaptainUserId(Long captainUserId) {
		this.captainUserId = captainUserId;
	}

}
