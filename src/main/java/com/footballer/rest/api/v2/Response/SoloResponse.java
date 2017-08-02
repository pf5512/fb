package com.footballer.rest.api.v2.Response;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.footballer.rest.api.v2.domain.SoloActiveCustomersInfo;
import com.footballer.rest.api.v2.domain.SoloWishInfo;

@JsonInclude(Include.NON_NULL) 
public class SoloResponse  extends JsonResponse {

	private Long soloWishId;
	private List<SoloWishInfo> soloWishList;
	private List<SoloActiveCustomersInfo> soloActiveCustomersList;
	
	public Long getSoloWishId() {
		return soloWishId;
	}
	public void setSoloWishId(Long soloWishId) {
		this.soloWishId = soloWishId;
	}
	public List<SoloWishInfo> getSoloWishList() {
		return soloWishList;
	}
	public void setSoloWishList(List<SoloWishInfo> soloWishList) {
		this.soloWishList = soloWishList;
	}
	public List<SoloActiveCustomersInfo> getSoloActiveCustomersList() {
		return soloActiveCustomersList;
	}
	public void setSoloActiveCustomersList(List<SoloActiveCustomersInfo> soloActiveCustomersList) {
		this.soloActiveCustomersList = soloActiveCustomersList;
	}

}
