package com.footballer.rest.api.v2.Response;

import java.util.List;

import com.footballer.rest.api.v2.result.EventCommentsResult;

public class EventCommentsResponse extends JsonResponse {
	
	private List<EventCommentsResult> ecList;
	
	public List<EventCommentsResult> getEcList() {
		return ecList;
	}
	public void setEcList(List<EventCommentsResult> ecList) {
		this.ecList = ecList;
	}
}
