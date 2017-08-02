package com.footballer.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class DateUtil {

	private final static String OLD_LONG_DATE = "yyyy-MM-dd H:m:s";

	private final static String SHORT_DATE_CHINA = "M月d日 EEEE HH:mm";
	private final static String PHONE_SHORT_DATE_CHINA = "M月d日 EEEE";
	private final static String WEEK_DATE_CHINA = "EEEE";
	private final static String PHONE_LONG_DATE_CHINA = "Y年M月d日 EEEE";
	public final static String LONG_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public final static String LONG_DATE_TIME_NO_SECOND = "yyyy-MM-dd HH:mm";
	private final static String SHORT_DATE = "yyyy-MM-dd";
	private final static String SHORT_TIME = "HH:mm:ss";
	private final static String NO_SECOND_TIME = "HH:mm";
	public static final SimpleDateFormat DATE_FORMAT_GROUP = new SimpleDateFormat("EEEE, MMM dd, yyyy");
	public static final SimpleDateFormat PHONE_DATE_FORMAT_GROUP = new SimpleDateFormat("EEEE, MMM dd");
	
	

	public static Date getCurrentDate() {
		return new Date();
	}
	
	public static String getCurrentDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	
	public static List<String> currentWeekDays() {
    	List<String> dates = new ArrayList<String>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Date currentDate = new Date();  
        List<Date> days = dateToWeek(currentDate);  
        for (Date date : days) {  
        	dates.add(sdf.format(date));  
        }
    	return dates;
    }
    
    public static List<Date> dateToWeek(Date mdate) {  
        Date fdate;  
        List<Date> list = new ArrayList<Date>();  
        for (int a = 0; a <= 6; a++) {  
            fdate = new Date();  
            fdate.setTime(mdate.getTime() + (a * 24 * 3600000));
            list.add(fdate);
        }  
        return list;  
    }
    
    public static List<String> currentWeekDaysByNumber(Integer weekAccount) {
    	List<String> dates = new ArrayList<String>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Date currentDate = new Date();  
        List<Date> days = dateToWeekByNumber(currentDate, weekAccount);  
        for (Date date : days) {  
        	dates.add(sdf.format(date));  
        }
    	return dates;
    }
    
    public static List<Date> dateToWeekByNumber(Date mdate, Integer weekAccount) {
    	Date fdate;
    	Integer weekDays = weekAccount * 6;
        List<Date> list = new ArrayList<Date>();  
        for (int a = 0; a <= weekDays; a++) {  
            fdate = new Date();  
            fdate.setTime(mdate.getTime() + (a * 24 * 3600000));
            list.add(fdate);
        }  
        return list;
    }

	public static String getSystemCurrentTime() {
		return DATE_FORMAT_GROUP.format(new Date());
	}
	
	public static String formatPhoneDateChina(Date date) {
		if(ObjectUtil.isNotNull(date)){
			DateTime dateTime = new DateTime(date);
			return DateTimeFormat.forPattern(PHONE_SHORT_DATE_CHINA).print(dateTime);
		}else{
			return null;
		}
	}
	
	
	public static String formatLongPhoneDateChina(Date date) {
		DateTime dateTime = new DateTime(date);
		return DateTimeFormat.forPattern(PHONE_LONG_DATE_CHINA).print(dateTime);
	}

	public static String formatOldLongDateChina(Date date) {
		DateTime dateTime = new DateTime(date);
		return DateTimeFormat.forPattern(OLD_LONG_DATE).print(dateTime);
	}

	public static Date parseOldLongDateChina(String date) {
		DateTime dateTime = DateTimeFormat.forPattern(OLD_LONG_DATE).parseDateTime(date);
		return dateTime.toDate();
	}

	public static String formatShortDateChina(Date date) {
		DateTime dateTime = new DateTime(date);
		return DateTimeFormat.forPattern(SHORT_DATE_CHINA).withLocale(Locale.CHINA).print(dateTime);
	}

	public static Date parseShortDateChina(String date) {
		DateTime dateTime = DateTimeFormat.forPattern(SHORT_DATE_CHINA).withLocale(Locale.CHINA).parseDateTime(date);
		return dateTime.toDate();
	}

	public static String formatLongDate(Date date) {
		DateTime dateTime = new DateTime(date);
		return DateTimeFormat.forPattern(LONG_DATE_TIME).print(dateTime);
	}
	
	public static String formatLongDateNoSecond(Date date) {
		DateTime dateTime = new DateTime(date);
		return DateTimeFormat.forPattern(LONG_DATE_TIME_NO_SECOND).print(dateTime);
	}
	
	public static String formatChinaWeekDate(Date date) {
		DateTime dateTime = new DateTime(date);
		return DateTimeFormat.forPattern(WEEK_DATE_CHINA).withLocale(Locale.CHINA).print(dateTime);
	}

	public static Date parseLongDate(String date) {
		DateTime dateTime = DateTimeFormat.forPattern(LONG_DATE_TIME).parseDateTime(date);
		return dateTime.toDate();
	}
	
	public static Date parseLongDateNoSecond(String date) {
		DateTime dateTime = DateTimeFormat.forPattern(LONG_DATE_TIME_NO_SECOND).parseDateTime(date);
		return dateTime.toDate();
	}

	public static String formatShortDate(Date date) {
		DateTime dateTime = new DateTime(date);
		return DateTimeFormat.forPattern(SHORT_DATE).print(dateTime);
	}

	public static Date parseShortDate(String date) {
		DateTime dateTime = DateTimeFormat.forPattern(SHORT_DATE).parseDateTime(date);
		return dateTime.toDate();
	}

	public static String formatShortTime(Date date) {
		DateTime dateTime = new DateTime(date);
		return DateTimeFormat.forPattern(SHORT_TIME).print(dateTime);
	}
	
	public static String formatNoSecondTime(Date date) {
		DateTime dateTime = new DateTime(date);
		return DateTimeFormat.forPattern(NO_SECOND_TIME).print(dateTime);
	}

	public static Date parseShortTime(String date) {
		DateTime dateTime = DateTimeFormat.forPattern(SHORT_TIME).parseDateTime(date);
		return dateTime.toDate();
	}
	
	
	public static Date parseNoSecondTime(String date) {
		DateTime dateTime = DateTimeFormat.forPattern(NO_SECOND_TIME).parseDateTime(date);
		return dateTime.toDate();
	}
	

	public static Date addHoursOfCurrentTime(double d) {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, (int) (d * 60));
		return c.getTime();
	}

	public static Date addHoursOfParameterDate(Date date, int hours) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, hours);
		return c.getTime();
	}

	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		return cal.get(Calendar.YEAR);
	}

	// 获取2个日期之间相差的天数
	public static long getBetweenDaysOfTwoDates(Date d1, Date d2) {
		long diff = d1.getTime() - d2.getTime(); // 这样得到的差值是微秒级别
		long days = diff / (1000 * 60 * 60 * 24);
		System.out.println("2个时间相距：" + Math.abs(days) + " days");
		return days;
	}

	// 获取2个日期之间相差的小时数
	public static long getBetweenHoursOfTwoDates(Date d1, Date d2) {
		long diff = d1.getTime() - d2.getTime(); // 这样得到的差值是微秒级别
		long days = diff / (1000 * 60 * 60 * 24);
		long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		System.out.println("2个时间相距：" + Math.abs(hours) + " hours");
		return hours;
	}

	// 获取2个日期之间相差的分数
	public static long getBetweenMinsOfTwoDates(Date d1, Date d2) {
		long diff = d1.getTime() - d2.getTime(); // 这样得到的差值是微秒级别
		long days = diff / (1000 * 60 * 60 * 24);
		long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long min = ((diff / (60 * 1000)) - days * 24 * 60 - hours * 60);
		System.out.println("2个时间相距：" + Math.abs(min) + " mins");
		return min;
	}

	// 获取2个日期之间相差的秒数
	public static long getBetweenSecondsOfTwoDates(Date d1, Date d2) {
		long diff = d1.getTime() - d2.getTime(); // 这样得到的差值是微秒级别
		long days = diff / (1000 * 60 * 60 * 24);
		long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long min = ((diff / (60 * 1000)) - days * 24 * 60 - hours * 60);
		long secs = (diff / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - min * 60);
		System.out.println("2个时间相距：" + Math.abs(secs) + " secs");
		return secs;
	}
	
	public static Date getNextWeekDay(Date currentDate, int dayOfweek)throws Exception{
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        if(week>(dayOfweek-1)){
            cal.add(Calendar.DAY_OF_MONTH,-(week-dayOfweek)+7);
        }else{
            cal.add(Calendar.DAY_OF_MONTH,dayOfweek-week+7);
        }
        return cal.getTime();
    }
	
	public static Date getNextSecondWeekDay(Date currentDate, int dayOfweek)throws Exception{
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        if(week>(dayOfweek-1)){
            cal.add(Calendar.DAY_OF_MONTH,-(week-dayOfweek)+14);
        }else{
            cal.add(Calendar.DAY_OF_MONTH,dayOfweek-week+14);
        }
        return cal.getTime();
    }
	
	public static void main(String args[]){		
		//System.out.println(currentWeekDaysByNumber(2));
	}
}
