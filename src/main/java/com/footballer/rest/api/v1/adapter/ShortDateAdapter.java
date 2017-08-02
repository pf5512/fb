package com.footballer.rest.api.v1.adapter;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.footballer.util.DateUtil;

public class ShortDateAdapter extends XmlAdapter<String, Date> {

	@Override
	public Date unmarshal(String v) throws Exception {
		Date date = null;
		try {
			date = DateUtil.parseShortDate(v);
		} catch (Exception e) {
			date = DateUtil.parseOldLongDateChina(v);
		}
		return date;
	}

	@Override
	public String marshal(Date v) throws Exception {
		return DateUtil.formatShortDate(v);
	}

}
