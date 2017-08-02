package com.footballer.rest.api.v1.vo.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.footballer.filter.AppContextHolder;
import com.footballer.rest.api.v1.adapter.LongDateAdapter;
import com.footballer.rest.api.v1.vo.Account;
import com.footballer.util.DateUtil;

@MappedSuperclass
public abstract class BaseRecordVo {
	
	private String createBy;
	private Date createDt;
	private String updateBy;
	private Date updateDt;
	
	public BaseRecordVo() {
	}
	
	public BaseRecordVo init() {
		
		if (null != AppContextHolder.getContext()) {
			Account account = AppContextHolder.getContext().getAccount();
			setCreateBy(String.valueOf(account.getId()));
			setUpdateBy(String.valueOf(account.getId()));
		}
		
		return initUpdate();
	}
	
	public BaseRecordVo initUpdate() {
		setCreateDt(DateUtil.getCurrentDate());
		setUpdateDt(DateUtil.getCurrentDate());
		
		return this;
	}
	
	@Column(name = "create_by", nullable = true, updatable = true)
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	@Column(name = "create_dt", nullable = true, updatable = true)
	@XmlJavaTypeAdapter(LongDateAdapter.class)
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	@Column(name = "update_by", nullable = true, updatable = true)
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	@Column(name = "update_dt", nullable = true, updatable = true)
	@XmlJavaTypeAdapter(LongDateAdapter.class)
	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}
}
