package com.footballer.rest.api.v1.response;

import java.util.List;

import com.footballer.rest.api.v1.result.PlayerWithTeamBaseInfoResult;

public class PlayerWithTeamBaseInfoResponse extends JsonResponse {

	private List<PlayerWithTeamBaseInfoResult> playerWithTeamBaseInfoList;

	public List<PlayerWithTeamBaseInfoResult> getPlayerWithTeamBaseInfoList() {
		return playerWithTeamBaseInfoList;
	}

	public void setPlayerWithTeamBaseInfoList(List<PlayerWithTeamBaseInfoResult> playerWithTeamBaseInfoList) {
		this.playerWithTeamBaseInfoList = playerWithTeamBaseInfoList;
	}
}
