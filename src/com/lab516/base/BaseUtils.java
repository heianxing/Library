package com.lab516.base;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * 工具类
 */
public abstract class BaseUtils {

	/** 判断字符串是否为空 */
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0 || "null".equals(str));
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/** 数据库插入时主键 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 计算分页第一条记录的位置
	 *
	 * @param pageNo
	 *            页号
	 * @param pageSize
	 *            一页数据条数
	 * @return
	 */
	public static int calcStart(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 计算分页页数
	 *
	 * @param pageSize
	 *            一页数据条数
	 * @param recordCount
	 *            记录数
	 * @return
	 */
	public static int calcPageCount(int pageSize, float recordCount) {
		return recordCount == 0 ? 1 : (int) Math.ceil(recordCount / pageSize);
	}

	/** 在开始和结束位置之间截取字符串 */
	public static String subStringBetween(String str, String start, String end) {
		int startIndex = str.indexOf(start);
		int endIndex = str.lastIndexOf(end);

		if (startIndex != -1 && endIndex != -1) {
			return str.substring(startIndex + 1, endIndex);
		} else {
			return null;
		}
	}

	public static Date getStartTimeOfDay(Date date) {
		return getDayWithHourMinSec(date, 0, 0, 0);
	}

	public static Date getEndTimeOfDay(Date date) {
		return getDayWithHourMinSec(date, 23, 59, 59);
	}

	private static Date getDayWithHourMinSec(Date date, int hour, int min, int sec) {
		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, sec);
		return cal.getTime();
	}

	public static boolean isEmpty(Comparable param) {
		return !isNotEmpty(param);
	}

	public static boolean isNotEmpty(Comparable param) {
		if (param instanceof String) {
			return param != null && param.toString().trim().length() > 0;
		} else {
			return param != null;
		}
	}

	public static int computeStart(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}

}
