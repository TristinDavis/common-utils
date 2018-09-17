package com.cweijan.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
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

	public static String formatDate(Date date) {
		if (date == null){
			return "";
		}
		return dateFormat.format(date);
	}
	
	/**
	 * 
	 * @return 返回时间戳 格式:yyyyMMddHHmmss
	 */
	public static String timestamp() {

		return getIntervalDay(0, "yyyyMMddHHmmssSSS");
	}

}
