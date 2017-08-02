package com.footballer.rest.api.v2.vo;

import java.util.Date;

import com.footballer.rest.api.v1.vo.enumType.HeavyFootType;
import com.footballer.rest.api.v1.vo.enumType.PlayerPosition;

public class TournamentPlayerBaseInfo {
	
	private Long playerId;
	private String playerName;
	private String playerLogo;
	private String wechatNo;
	private String wechatLogo; 
	private Date birth;
	private Integer height;
	private Integer weight;
	private HeavyFootType heavyFoot;
	private Integer playerNumber;
	private PlayerPosition position;
	
	private Long teamId;
	private String teamName;
	private String teamLogo;
	private String teamAbbr;

	private Long tournamentId;

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerLogo() {
		return playerLogo;
	}

	public void setPlayerLogo(String playerLogo) {
		this.playerLogo = playerLogo;
	}

	public String getWechatNo() {
		return wechatNo;
	}

	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}

	public String getWechatLogo() {
		return wechatLogo;
	}

	public void setWechatLogo(String wechatLogo) {
		this.wechatLogo = wechatLogo;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public HeavyFootType getHeavyFoot() {
		return heavyFoot;
	}

	public void setHeavyFoot(HeavyFootType heavyFoot) {
		this.heavyFoot = heavyFoot;
	}

	public Integer getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(Integer playerNumber) {
		this.playerNumber = playerNumber;
	}

	public PlayerPosition getPosition() {
		return position;
	}

	public void setPosition(PlayerPosition position) {
		this.position = position;
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

	public String getTeamAbbr() {
		return teamAbbr;
	}

	public void setTeamAbbr(String teamAbbr) {
		this.teamAbbr = teamAbbr;
	}

	public Long getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}
}
