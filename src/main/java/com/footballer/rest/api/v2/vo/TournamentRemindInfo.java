package com.footballer.rest.api.v2.vo;

public class TournamentRemindInfo {
	
	private Long playerId;
	private String tournamentName;
	private String arenaName;
	private String kickOffTime;
	private String teams;
	
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public String getTournamentName() {
		return tournamentName;
	}
	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}
	public String getArenaName() {
		return arenaName;
	}
	public void setArenaName(String arenaName) {
		this.arenaName = arenaName;
	}
	public String getKickOffTime() {
		return kickOffTime;
	}
	public void setKickOffTime(String kickOffTime) {
		this.kickOffTime = kickOffTime;
	}
	public String getTeams() {
		return teams;
	}
	public void setTeams(String teams) {
		this.teams = teams;
	}
	
	
	

}
