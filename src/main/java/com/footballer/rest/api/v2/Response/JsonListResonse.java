package com.footballer.rest.api.v2.Response;

import java.util.List;

public class JsonListResonse<T> {
	
	private List<T> list;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
