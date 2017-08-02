package simpleTest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.footballer.util.DateUtil;
import com.google.common.collect.Lists;

public class DateTest {

	public static void main(String[] args) {
		System.out.println("test");
		//seasonRange(2016);
		//monthRange(2016);

		all(2016);
	}

	public static void all(int year) {

		List<String> dataList = Lists.newArrayList();

		// 月度跨度
		for (int i = Calendar.JANUARY; i <= Calendar.DECEMBER; i++) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, i);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			Date startDate = cal.getTime();// 当前月第一天

			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			Date endDate = cal.getTime();// 当前月最后一天

			String times = (i+1) + "月-" + "36-24-7-3-2";
			// 1月-36-24-7-3-2
			dataList.add(times);
		}
		dataList.add("-");

		// 季度跨度
		for (int i = Calendar.JANUARY, j = 1; i < Calendar.DECEMBER; i += 3, j++) {

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, i);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			Date startDate = cal.getTime();// 当前月第一天

			cal.add(Calendar.MONTH, 3);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			Date endDate = cal.getTime();// 当前月最后一天

			String times = j + "季度-" + "36-24-7-3-2";
			// 1季度-36-24-7-3-2
			dataList.add(times);
		}

		for(String s:dataList){
			System.out.println(s);
		}
	}

	public static void seasonRange(int year) {

		for (int i = Calendar.JANUARY; i < Calendar.DECEMBER; i += 3) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, i);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			Date firstDate = cal.getTime();// 当前月第一天

			cal.add(Calendar.MONTH, 3);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			Date lastDate = cal.getTime();// 当前月最后一天

			String s = DateUtil.formatShortDate(firstDate);
			String d = DateUtil.formatShortDate(lastDate);

			System.out.println(s + "-" + d);

		}
	}

	public static void monthRange(int year) {

		for (int i = Calendar.JANUARY; i <= Calendar.DECEMBER; i++) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, i);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			Date firstDate = cal.getTime();// 当前月第一天

			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			Date lastDate = cal.getTime();// 当前月最后一天

			String s = DateUtil.formatShortDate(firstDate);
			String d = DateUtil.formatShortDate(lastDate);

			System.out.println(s + "-" + d);
		}
	}

}
