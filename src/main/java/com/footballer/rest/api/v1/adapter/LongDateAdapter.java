package com.footballer.rest.api.v1.adapter;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.footballer.util.DateUtil;

public class LongDateAdapter extends XmlAdapter<String, Date> {

    @Override
    public String marshal(Date v) {
        return DateUtil.formatLongDate(v);
    }

    @Override
    public Date unmarshal(String v) {
       Date date = null;
		try {
			date = DateUtil.parseLongDate(v);
		} catch (Exception e) {
			date = DateUtil.parseOldLongDateChina(v);
		}
		return date;
    }
}
