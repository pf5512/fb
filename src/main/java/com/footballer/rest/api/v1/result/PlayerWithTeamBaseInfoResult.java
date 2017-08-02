package com.footballer.rest.api.v1.result;

import java.util.List;

import com.footballer.rest.api.v1.domain.PlayerBaseInfo;
import com.footballer.rest.api.v1.domain.TeamBaseInfo;

public class PlayerWithTeamBaseInfoResult {

	private PlayerBaseInfo player;
	private List<TeamBaseInfo> teamList;
	
	public PlayerBaseInfo getPlayer() {
		return player;
	}
	public void setPlayer(PlayerBaseInfo player) {
		this.player = player;
	}
	public List<TeamBaseInfo> getTeamList() {
		return teamList;
	}
	public void setTeamList(List<TeamBaseInfo> teamList) {
		this.teamList = teamList;
	}
	
	
}
