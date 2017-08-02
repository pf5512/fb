package com.footballer.rest.api.v2.domain;

public class Sponsor {
	
	private Long sponsorId;
	private String sponsorName;
	private String sponsorLogo;
	private String sponsorUrl;
	private String sponsorDesc;
	private Long sponsorTel;
	
	private Long teamId;
	private String teamName;
	private String teamLogo;
	
	private Integer teamSponsorLevel;
	private Integer tournamentSponsorLevel;
	
	public Long getSponsorId() {
		return sponsorId;
	}
	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}
	public String getSponsorName() {
		return sponsorName;
	}
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	public String getSponsorLogo() {
		return sponsorLogo;
	}
	public void setSponsorLogo(String sponsorLogo) {
		this.sponsorLogo = sponsorLogo;
	}
	public String getSponsorUrl() {
		return sponsorUrl;
	}
	public void setSponsorUrl(String sponsorUrl) {
		this.sponsorUrl = sponsorUrl;
	}
	public String getSponsorDesc() {
		return sponsorDesc;
	}
	public void setSponsorDesc(String sponsorDesc) {
		this.sponsorDesc = sponsorDesc;
	}
	public Long getSponsorTel() {
		return sponsorTel;
	}
	public void setSponsorTel(Long sponsorTel) {
		this.sponsorTel = sponsorTel;
	}
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getTeamLogo() {
		return teamLogo;
	}
	public void setTeamLogo(String teamLogo) {
		this.teamLogo = teamLogo;
	}
	public Integer getTeamSponsorLevel() {
		return teamSponsorLevel;
	}
	public void setTeamSponsorLevel(Integer teamSponsorLevel) {
		this.teamSponsorLevel = teamSponsorLevel;
	}
	public Integer getTournamentSponsorLevel() {
		return tournamentSponsorLevel;
	}
	public void setTournamentSponsorLevel(Integer tournamentSponsorLevel) {
		this.tournamentSponsorLevel = tournamentSponsorLevel;
	}
}
