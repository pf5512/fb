package com.footballer.rest.api.v2.manager.schdule;

import com.footballer.rest.api.v2.vo.TournamentTeamBaseInfo;

public class ScheduleMatchItem {
	
	private Long hostTeamId;
	private Long guestTeamId;
	
	private TournamentTeamBaseInfo hostTeam;
	private TournamentTeamBaseInfo gestTeam;
	
	private String name;
	
	private Integer round;
	
	public ScheduleMatchItem() {
		
	}
	
	public ScheduleMatchItem(Long hostTeamId, Long guestTeamId, Integer round) {
		this.hostTeamId = hostTeamId;
		this.guestTeamId = guestTeamId;
		this.round = round;
	}

	public Long getHostTeamId() {
		return hostTeamId;
	}

	public void setHostTeamId(Long hostTeamId) {
		this.hostTeamId = hostTeamId;
	}

	public Long getGuestTeamId() {
		return guestTeamId;
	}

	public void setGuestTeamId(Long guestTeamId) {
		this.guestTeamId = guestTeamId;
	}

	public TournamentTeamBaseInfo getHostTeam() {
		return hostTeam;
	}

	public void setHostTeam(TournamentTeamBaseInfo hostTeam) {
		this.hostTeam = hostTeam;
	}

	public TournamentTeamBaseInfo getGestTeam() {
		return gestTeam;
	}

	public void setGestTeam(TournamentTeamBaseInfo gestTeam) {
		this.gestTeam = gestTeam;
	}

	public String getName() {
		return hostTeam.getTeamName() + " - " + gestTeam.getTeamName();
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRound() {
		return round;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

}
