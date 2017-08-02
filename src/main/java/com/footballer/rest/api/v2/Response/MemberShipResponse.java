package com.footballer.rest.api.v2.Response;

import java.util.List;

import com.footballer.rest.api.v2.domain.ArenaMemberInfo;
import com.footballer.rest.api.v2.result.MemberShipResult;
import com.footballer.rest.api.v2.vo.ArenaMemberBalanceLine;
import com.footballer.rest.api.v2.vo.ArenaMemberDiscount;

public class MemberShipResponse extends JsonResponse {
	
	private MemberShipResult memberShipResult;
	
	private List<ArenaMemberInfo> arenaMemberList;
	
	private List<ArenaMemberInfo> arenaNonMemberList;
	
	private List<ArenaMemberBalanceLine> arenaMemberBalanceLinesList;
	
	private List<ArenaMemberDiscount> arenaMemberDiscountList;

	public MemberShipResult getMemberShipResult() {
		return memberShipResult;
	}

	public void setMemberShipResult(MemberShipResult memberShipResult) {
		this.memberShipResult = memberShipResult;
	}

	public List<ArenaMemberInfo> getArenaMemberList() {
		return arenaMemberList;
	}

	public void setArenaMemberList(List<ArenaMemberInfo> arenaMemberList) {
		this.arenaMemberList = arenaMemberList;
	}

	public List<ArenaMemberBalanceLine> getArenaMemberBalanceLinesList() {
		return arenaMemberBalanceLinesList;
	}

	public void setArenaMemberBalanceLinesList(List<ArenaMemberBalanceLine> arenaMemberBalanceLinesList) {
		this.arenaMemberBalanceLinesList = arenaMemberBalanceLinesList;
	}

	public List<ArenaMemberDiscount> getArenaMemberDiscountList() {
		return arenaMemberDiscountList;
	}

	public void setArenaMemberDiscountList(List<ArenaMemberDiscount> arenaMemberDiscountList) {
		this.arenaMemberDiscountList = arenaMemberDiscountList;
	}

	public List<ArenaMemberInfo> getArenaNonMemberList() {
		return arenaNonMemberList;
	}

	public void setArenaNonMemberList(List<ArenaMemberInfo> arenaNonMemberList) {
		this.arenaNonMemberList = arenaNonMemberList;
	}
	
}
