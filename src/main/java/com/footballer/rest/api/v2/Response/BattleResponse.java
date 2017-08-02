package com.footballer.rest.api.v2.Response;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.footballer.rest.api.v2.domain.BattleActiveCustomersInfo;
import com.footballer.rest.api.v2.domain.BattleWishInfo;

@JsonInclude(Include.NON_NULL) 
public class BattleResponse  extends JsonResponse {

	private Long battleWishId;
	private List<BattleWishInfo> battleWishList;
	private List<BattleActiveCustomersInfo> battleActiveCustomersList;

	public List<BattleWishInfo> getBattleWishList() {
		return battleWishList;
	}

	public void setBattleWishList(List<BattleWishInfo> battleWishList) {
		this.battleWishList = battleWishList;
	}

	public Long getBattleWishId() {
		return battleWishId;
	}

	public void setBattleWishId(Long battleWishId) {
		this.battleWishId = battleWishId;
	}

	public List<BattleActiveCustomersInfo> getBattleActiveCustomersList() {
		return battleActiveCustomersList;
	}

	public void setBattleActiveCustomersList(List<BattleActiveCustomersInfo> battleActiveCustomersList) {
		this.battleActiveCustomersList = battleActiveCustomersList;
	}
}
