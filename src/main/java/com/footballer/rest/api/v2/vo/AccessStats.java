package com.footballer.rest.api.v2.vo;

import java.util.Date;

import com.footballer.util.DateUtil;

public class AccessStats {

    
	private Long id;
	private String url;
	private String page;
	private Integer times;
	private Date date;
	private String sdate;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getSdate() {
		return DateUtil.formatShortDate(date);
	}
//	public void setSdate(Date date) {
//		this.sdate = DateUtil.formatShortDate(date);
//	}
	
}
