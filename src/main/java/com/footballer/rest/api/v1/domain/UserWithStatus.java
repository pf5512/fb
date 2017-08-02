package com.footballer.rest.api.v1.domain;

public class UserWithStatus {
	
	private Long id;
	private String name;
	private String cellphoneNO;
    private String imageUrl;
	private Boolean isRegistered;
	private Long fromPlayerId;
	private Long playerId;
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getCellphoneNO() {
		return cellphoneNO;
	}
	public void setCellphoneNO(String cellphoneNO) {
		this.cellphoneNO = cellphoneNO;
	}
	public Boolean getIsRegistered() {
		return isRegistered;
	}
	public void setIsRegistered(Boolean isRegistered) {
		this.isRegistered = isRegistered;
	}
	public Long getFromPlayerId() {
		return fromPlayerId;
	}
	public void setFromPlayerId(Long fromPlayerId) {
		this.fromPlayerId = fromPlayerId;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
