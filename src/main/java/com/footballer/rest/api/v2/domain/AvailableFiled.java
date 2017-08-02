package com.footballer.rest.api.v2.domain;

public class AvailableFiled {
	private Long fieldId;
	private String fieldName;
	private Long fcsId;
	
	
	public Long getFieldId() {
		return fieldId;
	}
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Long getFcsId() {
		return fcsId;
	}
	public void setFcsId(Long fcsId) {
		this.fcsId = fcsId;
	}
}
