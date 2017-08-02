package com.footballer.rest.api.v1.response;

import java.util.List;

public class ListResponse<T> extends JsonResponse {
	
	private List<T> list;
	
	public ListResponse() {
		
	}
	
	public ListResponse(List<T> list) {
		this.list = list;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	

}
