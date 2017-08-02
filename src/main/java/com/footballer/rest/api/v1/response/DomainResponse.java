package com.footballer.rest.api.v1.response;

import java.util.List;

/**
 * Created by ian on 8/29/14.
 */
public class DomainResponse<T> extends JsonResponse{

    private T domain;
    private List<?> list;

    public DomainResponse() {

    }

    public DomainResponse(T domain) {
        this.domain = domain;
    }

    public T getDomain() {
        return domain;
    }

    public void setDomain(T domain) {
        this.domain = domain;
    }

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

}
