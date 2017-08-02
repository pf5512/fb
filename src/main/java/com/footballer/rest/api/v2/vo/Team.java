package com.footballer.rest.api.v2.vo;

import com.footballer.rest.api.v2.vo.base.GenericVo;

public class Team extends GenericVo {
	
	private String name;
	private String logo;
	private Long captainUserId;
	private Long tournamentId;
	
	public Long getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Long getCaptainUserId() {
		return captainUserId;
	}
	public void setCaptainUserId(Long captainUserId) {
		this.captainUserId = captainUserId;
	}

}
