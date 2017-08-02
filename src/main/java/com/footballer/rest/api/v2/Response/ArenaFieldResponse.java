package com.footballer.rest.api.v2.Response;

import java.util.List;

import com.footballer.rest.api.v2.vo.FieldChargeStandard;

public class ArenaFieldResponse extends JsonResponse {
	
	
	private FieldChargeStandard fieldChargeStandard;
	private List<FieldChargeStandard> fieldChargeStandardList;

	public List<FieldChargeStandard> getFieldChargeStandardList() {
		return fieldChargeStandardList;
	}

	public void setFieldChargeStandardList(List<FieldChargeStandard> fieldChargeStandardList) {
		this.fieldChargeStandardList = fieldChargeStandardList;
	}

	public FieldChargeStandard getFieldChargeStandard() {
		return fieldChargeStandard;
	}

	public void setFieldChargeStandard(FieldChargeStandard fieldChargeStandard) {
		this.fieldChargeStandard = fieldChargeStandard;
	}
	
	
}
