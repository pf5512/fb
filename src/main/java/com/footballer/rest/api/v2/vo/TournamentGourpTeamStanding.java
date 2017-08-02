package com.footballer.rest.api.v2.vo;

public class TournamentGourpTeamStanding {

	private String groupName;
	private Long teamId;
	private String teamName;
	private String teamLogo;
	private Integer totalMatches;
	private Integer totalWin;
	private Integer totalDraw;
	private Integer totalLose;
	private Integer totalGoals;
	private Integer totalConceded;
	private Integer totalGoalDifference;
	private Integer totalPoints;
	
	private Integer totalPTSFor;
	private Integer totalPTSAgainst;
	private Integer totalPTSDiff;
	
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
	public Integer getTotalMatches() {
		return totalMatches;
	}
	public void setTotalMatches(Integer totalMatches) {
		this.totalMatches = totalMatches;
	}
	public Integer getTotalWin() {
		return totalWin;
	}
	public void setTotalWin(Integer totalWin) {
		this.totalWin = totalWin;
	}
	public Integer getTotalDraw() {
		return totalDraw;
	}
	public void setTotalDraw(Integer totalDraw) {
		this.totalDraw = totalDraw;
	}
	public Integer getTotalLose() {
		return totalLose;
	}
	public void setTotalLose(Integer totalLose) {
		this.totalLose = totalLose;
	}
	public Integer getTotalGoals() {
		return totalGoals;
	}
	public void setTotalGoals(Integer totalGoals) {
		this.totalGoals = totalGoals;
	}
	public Integer getTotalConceded() {
		return totalConceded;
	}
	public void setTotalConceded(Integer totalConceded) {
		this.totalConceded = totalConceded;
	}
	public Integer getTotalGoalDifference() {
		return totalGoalDifference;
	}
	public void setTotalGoalDifference(Integer totalGoalDifference) {
		this.totalGoalDifference = totalGoalDifference;
	}
	public Integer getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getTotalPTSFor() {
		return totalPTSFor;
	}
	public void setTotalPTSFor(Integer totalPTSFor) {
		this.totalPTSFor = totalPTSFor;
	}
	public Integer getTotalPTSAgainst() {
		return totalPTSAgainst;
	}
	public void setTotalPTSAgainst(Integer totalPTSAgainst) {
		this.totalPTSAgainst = totalPTSAgainst;
	}
	public Integer getTotalPTSDiff() {
		return totalPTSDiff;
	}
	public void setTotalPTSDiff(Integer totalPTSDiff) {
		this.totalPTSDiff = totalPTSDiff;
	}
	
	
}
