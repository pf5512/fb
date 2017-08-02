package com.footballer.rest.api.v2.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL) 
public class Dashboard {
	private Player player;
	private List<PlayerMutualEvaluationMatchCount> evaluationList;
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public List<PlayerMutualEvaluationMatchCount> getEvaluationList() {
		return evaluationList;
	}
	public void setEvaluationList(
			List<PlayerMutualEvaluationMatchCount> evaluationList) {
		this.evaluationList = evaluationList;
	}
	
	
}
