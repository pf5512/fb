package com.footballer.rest.api.v2.Response;

import java.util.List;

import com.footballer.rest.api.v2.vo.Player;

public class EvaluationResponse extends JsonResponse {
	private List<Player> recognisedPlayers;

	public List<Player> getRecognisedPlayers() {
		return recognisedPlayers;
	}

	public void setRecognisedPlayers(List<Player> recognisedPlayers) {
		this.recognisedPlayers = recognisedPlayers;
	}
	
	
}
