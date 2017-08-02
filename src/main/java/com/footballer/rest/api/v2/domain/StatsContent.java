package com.footballer.rest.api.v2.domain;

public class StatsContent {

	private Long id;
	
	private Boolean mvp;
	private Boolean goals;
	private Boolean assists;
	private Boolean yellowCard;
	private Boolean redCard;
	private Boolean penalty;
	
	private Boolean shots;
	private Boolean longShot;
	private Boolean shotOnTarget;
	
	private Boolean pass;
	private Boolean longPass;
	private Boolean passCompleted;
	private Boolean passFinalBall;
		
	private Boolean tackles;
	private Boolean block;
	private Boolean keyDefense;
	private Boolean save;
	
	private Boolean loosePossession;
	private Boolean distanceCoverd;

	private Boolean offside;
	private Boolean ownGoals;
	
	private Boolean fouls;
	
	private Boolean teamMVP;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getMvp() {
		return mvp;
	}
	public void setMvp(Boolean mvp) {
		this.mvp = mvp;
	}
	public Boolean getGoals() {
		return goals;
	}
	public void setGoals(Boolean goals) {
		this.goals = goals;
	}
	public Boolean getAssists() {
		return assists;
	}
	public void setAssists(Boolean assists) {
		this.assists = assists;
	}
	public Boolean getYellowCard() {
		return yellowCard;
	}
	public void setYellowCard(Boolean yellowCard) {
		this.yellowCard = yellowCard;
	}
	public Boolean getRedCard() {
		return redCard;
	}
	public void setRedCard(Boolean redCard) {
		this.redCard = redCard;
	}
	public Boolean getPenalty() {
		return penalty;
	}
	public void setPenalty(Boolean penalty) {
		this.penalty = penalty;
	}
	public Boolean getShots() {
		return shots;
	}
	public void setShots(Boolean shots) {
		this.shots = shots;
	}
	public Boolean getLongShot() {
		return longShot;
	}
	public void setLongShot(Boolean longShot) {
		this.longShot = longShot;
	}
	public Boolean getShotOnTarget() {
		return shotOnTarget;
	}
	public void setShotOnTarget(Boolean shotOnTarget) {
		this.shotOnTarget = shotOnTarget;
	}
	public Boolean getPass() {
		return pass;
	}
	public void setPass(Boolean pass) {
		this.pass = pass;
	}
	public Boolean getLongPass() {
		return longPass;
	}
	public void setLongPass(Boolean longPass) {
		this.longPass = longPass;
	}
	public Boolean getPassCompleted() {
		return passCompleted;
	}
	public void setPassCompleted(Boolean passCompleted) {
		this.passCompleted = passCompleted;
	}
	public Boolean getPassFinalBall() {
		return passFinalBall;
	}
	public void setPassFinalBall(Boolean passFinalBall) {
		this.passFinalBall = passFinalBall;
	}
	public Boolean getTackles() {
		return tackles;
	}
	public void setTackles(Boolean tackles) {
		this.tackles = tackles;
	}
	public Boolean getBlock() {
		return block;
	}
	public void setBlock(Boolean block) {
		this.block = block;
	}
	public Boolean getKeyDefense() {
		return keyDefense;
	}
	public void setKeyDefense(Boolean keyDefense) {
		this.keyDefense = keyDefense;
	}
	public Boolean getLoosePossession() {
		return loosePossession;
	}
	public void setLoosePossession(Boolean loosePossession) {
		this.loosePossession = loosePossession;
	}
	public Boolean getDistanceCoverd() {
		return distanceCoverd;
	}
	public void setDistanceCoverd(Boolean distanceCoverd) {
		this.distanceCoverd = distanceCoverd;
	}
	public Boolean getOffside() {
		return offside;
	}
	public void setOffside(Boolean offside) {
		this.offside = offside;
	}
	public Boolean getOwnGoals() {
		return ownGoals;
	}
	public void setOwnGoals(Boolean ownGoals) {
		this.ownGoals = ownGoals;
	}
	public Boolean getFouls() {
		return fouls;
	}
	public void setFouls(Boolean fouls) {
		this.fouls = fouls;
	}
	public Boolean getSave() {
		return save;
	}
	public void setSave(Boolean save) {
		this.save = save;
	}
	public Boolean getTeamMVP() {
		return teamMVP;
	}
	public void setTeamMVP(Boolean teamMVP) {
		this.teamMVP = teamMVP;
	}
}
