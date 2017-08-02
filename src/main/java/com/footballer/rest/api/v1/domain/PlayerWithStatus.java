package com.footballer.rest.api.v1.domain;

import com.footballer.rest.api.v1.vo.enumType.PlayerTeamActivityType;


public class PlayerWithStatus {
	private Long id;
	private String name;
    private String imageUrl;
	private String invitationResponseType;
	private Integer takeFriendsNO;
	private Boolean isSigned;
	private Boolean isAdmin;
	private Boolean isInCurrentEventsTeam;
	private PlayerTeamActivityType playerTeamActivityType;
	
	public Integer getTakeFriendsNO() {
		return takeFriendsNO;
	}
	public void setTakeFriendsNO(Integer takeFriendsNO) {
		this.takeFriendsNO = takeFriendsNO;
	}
	public Boolean isSigned() {
		return isSigned;
	}
	public void setSigned(Boolean isSigned) {
		this.isSigned = isSigned;
	}
	public Boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getInvitationResponseType() {
		return invitationResponseType;
	}
	public void setInvitationResponseType(String invitationResponseType) {
		this.invitationResponseType = invitationResponseType;
	}
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
	
	public PlayerTeamActivityType getPlayerTeamActivityType() {
		return playerTeamActivityType;
	}
	public void setPlayerTeamActivityType(
			PlayerTeamActivityType playerTeamActivityType) {
		this.playerTeamActivityType = playerTeamActivityType;
	}
	public Boolean getIsInCurrentEventsTeam() {
		return isInCurrentEventsTeam;
	}
	public void setIsInCurrentEventsTeam(Boolean isInCurrentEventsTeam) {
		this.isInCurrentEventsTeam = isInCurrentEventsTeam;
	}


}
