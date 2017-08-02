package com.footballer.rest.api.v2.Response;

import java.util.List;

import com.footballer.rest.api.v2.vo.PlayerMutualEvaluationAllCount;
import com.footballer.rest.api.v2.vo.PlayerMutualEvaluationMatchCount;

public class EvaluationCountResponse extends JsonResponse {
	private PlayerMutualEvaluationAllCount evaluationAllCount;
	
	private List<PlayerMutualEvaluationAllCount> evaluationAllCountList;
	
	private List<PlayerMutualEvaluationMatchCount> evaluationMatchCountList;
	
	private PlayerMutualEvaluationMatchCount playerMutualEvaluationMatchCount;

	public PlayerMutualEvaluationMatchCount getPlayerMutualEvaluationMatchCount() {
		return playerMutualEvaluationMatchCount;
	}

	public void setPlayerMutualEvaluationMatchCount(
			PlayerMutualEvaluationMatchCount playerMutualEvaluationMatchCount) {
		this.playerMutualEvaluationMatchCount = playerMutualEvaluationMatchCount;
	}

	public PlayerMutualEvaluationAllCount getEvaluationAllCount() {
		return evaluationAllCount;
	}

	public void setEvaluationAllCount(
			PlayerMutualEvaluationAllCount evaluationAllCount) {
		this.evaluationAllCount = evaluationAllCount;
	}

	public List<PlayerMutualEvaluationAllCount> getEvaluationAllCountList() {
		return evaluationAllCountList;
	}

	public void setEvaluationAllCountList(
			List<PlayerMutualEvaluationAllCount> evaluationAllCountList) {
		this.evaluationAllCountList = evaluationAllCountList;
	}

	public List<PlayerMutualEvaluationMatchCount> getEvaluationMatchCountList() {
		return evaluationMatchCountList;
	}

	public void setEvaluationMatchCountList(
			List<PlayerMutualEvaluationMatchCount> evaluationMatchCountList) {
		this.evaluationMatchCountList = evaluationMatchCountList;
	}
	
	
	
}
