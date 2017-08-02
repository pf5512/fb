package com.footballer.rest.api.v2.request;

import java.math.BigDecimal;
import java.util.Date;

import com.footballer.rest.api.v2.vo.base.GenericVo;
import com.footballer.util.DateUtil;
import com.footballer.util.StringUtil;

public class FieldChargeStandardRequest{
	
	private Long id;
	private Long arenaId;
	private Long fieldId;
	private String startTime;
	private String endTime;
	private BigDecimal price;
	private BigDecimal weekendPrice;
	private BigDecimal barginPrice;


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getArenaId() {
		return arenaId;
	}
	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
	}
	public Long getFieldId() {
		return fieldId;
	}
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getBarginPrice() {
		return barginPrice;
	}
	public void setBarginPrice(BigDecimal barginPrice) {
		this.barginPrice = barginPrice;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public BigDecimal getWeekendPrice() {
		return weekendPrice;
	}
	public void setWeekendPrice(BigDecimal weekendPrice) {
		this.weekendPrice = weekendPrice;
	}

}
