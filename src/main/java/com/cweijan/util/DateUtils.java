package com.cweijan.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

	/**
	 * 获取n天之前的日期
	 * 
	 * @param intervalDay
	 * @return 格式 yyyy-MM-dd
	 */
	public static String getIntervalDay(int intervalDay) {

		return getIntervalDay(intervalDay, "yyyy-MM-dd");
	}

	public static String getIntervalDay(int intervalDay, String formatter) {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, intervalDay);

		return new SimpleDateFormat(formatter).format(calendar.getTime());
	}

	/**
	 * 
	 * @return 返回日期 格式:yyyy-MM-dd HH:mm:ss
	 */
	public static String now() {

		return getIntervalDay(0, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String now(String formatter) {

		return getIntervalDay(0, formatter);
	}

	/**
	 * 
	 * @return 返回时间戳 格式:yyyyMMddHHmmss
	 */
	public static String timestamp() {

		return getIntervalDay(0, "yyyyMMddHHmmssSSS");
	}

}
