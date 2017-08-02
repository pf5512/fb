package com.footballer.rest.api.v1.response;

import java.util.List;

import com.footballer.rest.api.v2.domain.AvailableFiled;

/**
 * Created by justin on 7/23/16.
 */

public class FieldResponse extends JsonResponse {

	private List<AvailableFiled> avaliableFieldsList;

	public List<AvailableFiled> getAvaliableFieldsList() {
		return avaliableFieldsList;
	}

	public void setAvaliableFieldsList(List<AvailableFiled> avaliableFieldsList) {
		this.avaliableFieldsList = avaliableFieldsList;
	}
	
}
