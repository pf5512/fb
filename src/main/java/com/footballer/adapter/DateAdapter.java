package com.footballer.adapter;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateAdapter extends XmlAdapter<String, Date> {
	
	//private static final String FORMAT = "dd/MM/yyyy HH:mm:ss";
	private static final String FORMAT = "yyyy-MM-dd:HH:mm";

	@Override
	public Date unmarshal(String date) throws Exception {
		final DateTimeFormatter format = DateTimeFormat.forPattern(FORMAT);
		final DateTime dateTime = format.parseDateTime(date);
		return dateTime.toDate();
	}

	@Override
	public String marshal(Date v) throws Exception {
		final DateTime dateTime = new DateTime(v);
		final DateTimeFormatter formatter = DateTimeFormat.forPattern(FORMAT);
		return dateTime.toString(formatter);
	}
	
	public static void main(String[] args) throws Exception {
		DateAdapter dateAdapter = new DateAdapter();
		System.out.println(dateAdapter.marshal(new Date()));
		
		String date = "2017-04-28:23:05";
		System.out.println(dateAdapter.unmarshal(date));
	}
	
}

