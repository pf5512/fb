package com.footballer.rest.api.v1.response;

import com.footballer.rest.api.v1.vo.enumType.PlayerTeamActivityType;

public class SignResponse extends JsonResponse {

	private PlayerTeamActivityType playerTeamActivityType;

	public PlayerTeamActivityType getPlayerTeamActivityType() {
		return playerTeamActivityType;
	}

	public void setPlayerTeamActivityType(
			PlayerTeamActivityType playerTeamActivityType) {
		this.playerTeamActivityType = playerTeamActivityType;
	}
}
