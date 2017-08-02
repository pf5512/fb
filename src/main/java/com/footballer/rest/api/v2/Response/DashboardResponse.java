package com.footballer.rest.api.v2.Response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.footballer.rest.api.v2.vo.Dashboard;
import com.footballer.rest.api.v2.vo.Player;

@JsonInclude(Include.NON_NULL) 
public class DashboardResponse  extends JsonResponse {
	private List<Dashboard> evaluations;
	
	private List<Player> recogniseList;

	public List<Dashboard> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<Dashboard> evaluations) {
		this.evaluations = evaluations;
	}

	public List<Player> getRecogniseList() {
		return recogniseList;
	}

	public void setRecogniseList(List<Player> recogniseList) {
		this.recogniseList = recogniseList;
	}

	
	
	
}
