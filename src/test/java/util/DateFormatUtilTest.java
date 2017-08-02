package util;

import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import com.footballer.util.DateUtil;

public class DateFormatUtilTest {
	
	@Test
	public void testFormatShortDate() {
		int year = 2015;
		int month = 05;
		int date = 17;
		int hrs = 19;
		int min = 30;
		
		Calendar c = Calendar.getInstance();
		c.set(year, month, date, hrs, min);
		
		String dateString = DateUtil.formatShortDateChina(c.getTime());
		
		Assert.assertEquals("6月17日 星期三 19:30", dateString);
	}
	
	@Test
	public void testCurrentDate() {
		String pattern = "yyyy-MM-dd hh:mm:ss";  
		  
		DateTime dateTime = new DateTime();  
		String dateStr = dateTime.toString(pattern);
		System.out.println(dateTime.toDate());
		
		System.out.println(dateStr);
		  
		DateTime date = DateTimeFormat.forPattern(pattern).parseDateTime(dateStr);
		
		System.out.println(date.toDate());
		System.out.println(new Date());
		
		Assert.assertNotNull(date.toDate());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testParseShortDateChina() {
		String date = "2015-07-20 22:5:0";
		DateUtil.parseShortDateChina(date);
	}
	
	@Test
	public void testParseShortDateChinaCompatible() {
		Date result = null;
		String date = "2015-07-20 22:5:0";
		
		try {
			result = DateUtil.parseShortDateChina(date);
		} catch (Exception e) {
			result = DateUtil.parseOldLongDateChina(date);
		}
		
		Assert.assertNotNull(result);
	}

}
