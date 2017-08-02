package com.footballer.rest.api.v1.request;


public class GetAppUserRequest {
	
	private Long teamId;
	private String cellphoneList;
	
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	public String getCellphoneList() {
		return cellphoneList;
	}
	public void setCellphoneList(String cellphoneList) {
		this.cellphoneList = cellphoneList;
	}
}