package com.footballer.rest.api.v2.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TeamMatchResult {
	 
	 private Long tournamentMatchId;
	 private Long teamId;
	 private String teamName;
	 private String teamAbbr;
	 private String teamLogo;
	 private Integer matches;
	 private Integer loosePossession;
	 private Integer distanceCoverd;
	 private Integer longShot;
	 private Integer shots;
	 private Integer shotOnTarget;
	 private Integer goals;
	 private Integer passFinalBall;
	 private Integer assists;
	 private Integer fouls;
	 private Integer tackles;
	 private Integer block;
	 private Integer keyDefense;
	 private Integer pass;
	 private Integer longPass;
	 private Integer passCompleted;
	 private Integer yellowCard;
	 private Integer redCard;
	 private Integer offside;
	 private Integer save;
	 private Integer MVP;
	 
	 private Integer playerId;
	 private Integer points;
	 private Integer conceded;
	 private Integer ownGoals;
	 
	 private String matchName;
	 private Date matchTime;
	 
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
	public Integer getMatches() {
		return matches;
	}
	public void setMatches(Integer matches) {
		this.matches = matches;
	}
	public Integer getLoosePossession() {
		return loosePossession;
	}
	public void setLoosePossession(Integer loosePossession) {
		this.loosePossession = loosePossession;
	}
	public Integer getDistanceCoverd() {
		return distanceCoverd;
	}
	public void setDistanceCoverd(Integer distanceCoverd) {
		this.distanceCoverd = distanceCoverd;
	}
	public Integer getLongShot() {
		return longShot;
	}
	public void setLongShot(Integer longShot) {
		this.longShot = longShot;
	}
	public Integer getShots() {
		return shots;
	}
	public void setShots(Integer shots) {
		this.shots = shots;
	}
	public Integer getShotOnTarget() {
		return shotOnTarget;
	}
	public void setShotOnTarget(Integer shotOnTarget) {
		this.shotOnTarget = shotOnTarget;
	}
	public Integer getGoals() {
		return goals;
	}
	public void setGoals(Integer goals) {
		this.goals = goals;
	}
	public Integer getPassFinalBall() {
		return passFinalBall;
	}
	public void setPassFinalBall(Integer passFinalBall) {
		this.passFinalBall = passFinalBall;
	}
	public Integer getAssists() {
		return assists;
	}
	public void setAssists(Integer assists) {
		this.assists = assists;
	}
	public Integer getFouls() {
		return fouls;
	}
	public void setFouls(Integer fouls) {
		this.fouls = fouls;
	}
	public Integer getTackles() {
		return tackles;
	}
	public void setTackles(Integer tackles) {
		this.tackles = tackles;
	}
	public Integer getBlock() {
		return block;
	}
	public void setBlock(Integer block) {
		this.block = block;
	}
	public Integer getKeyDefense() {
		return keyDefense;
	}
	public void setKeyDefense(Integer keyDefense) {
		this.keyDefense = keyDefense;
	}
	public Integer getPass() {
		return pass;
	}
	public void setPass(Integer pass) {
		this.pass = pass;
	}
	public Integer getLongPass() {
		return longPass;
	}
	public void setLongPass(Integer longPass) {
		this.longPass = longPass;
	}
	public Integer getPassCompleted() {
		return passCompleted;
	}
	public void setPassCompleted(Integer passCompleted) {
		this.passCompleted = passCompleted;
	}
	public Integer getYellowCard() {
		return yellowCard;
	}
	public void setYellowCard(Integer yellowCard) {
		this.yellowCard = yellowCard;
	}
	public Integer getRedCard() {
		return redCard;
	}
	public void setRedCard(Integer redCard) {
		this.redCard = redCard;
	}
	public Integer getOffside() {
		return offside;
	}
	public void setOffside(Integer offside) {
		this.offside = offside;
	}
	public Integer getSave() {
		return save;
	}
	public void setSave(Integer save) {
		this.save = save;
	}
	public Integer getMVP() {
		return MVP;
	}
	public void setMVP(Integer mVP) {
		MVP = mVP;
	}
	public Long getTournamentMatchId() {
		return tournamentMatchId;
	}
	public void setTournamentMatchId(Long tournamentMatchId) {
		this.tournamentMatchId = tournamentMatchId;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Integer getConceded() {
		return conceded;
	}
	public void setConceded(Integer conceded) {
		this.conceded = conceded;
	}
	public Integer getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}
	public Integer getOwnGoals() {
		return ownGoals;
	}
	public void setOwnGoals(Integer ownGoals) {
		this.ownGoals = ownGoals;
	}
	public String getTeamAbbr() {
		return teamAbbr;
	}
	public void setTeamAbbr(String teamAbbr) {
		this.teamAbbr = teamAbbr;
	}
	public String getMatchName() {
		return matchName;
	}
	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}
	public Date getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}
}
