package com.footballer.rest.api.v2.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.footballer.util.DateUtil;
import com.footballer.util.StringUtil;

public class FieldChargeStandard{
	
	private Long id;
	private Long arenaId;
	private Long fieldId;
	private Date startTime;
	private Date endTime;
	private String disPlayStartTime;
	private String disPlayEndTime;
	private BigDecimal price;
	private BigDecimal weekendPrice;
	private BigDecimal barginPrice;
	private String name;


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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public String getDisPlayStartTime() {
		//仅显示 时：分
		String displayStartTime	= DateUtil.formatShortTime(startTime);
		return StringUtil.subString(displayStartTime, 5);
	}
	public String getDisPlayEndTime() {
		//仅显示 时：分
		String displayEndTime	= DateUtil.formatShortTime(endTime);
		return StringUtil.subString(displayEndTime, 5);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getWeekendPrice() {
		return weekendPrice;
	}
	public void setWeekendPrice(BigDecimal weekendPrice) {
		this.weekendPrice = weekendPrice;
	}
	
}
