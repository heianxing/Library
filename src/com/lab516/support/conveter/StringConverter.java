package com.lab516.support.conveter;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;

import com.lab516.base.BaseUtils;
import com.lab516.base.Consts;

public class StringConverter {

	private static final Pattern PATTERN_YMD = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

	private static final Pattern PATTERN_YMD_HMS = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");

	private static final Pattern PATTERN_HMS = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");

	private static final String[] TRUES = { "true", "1", "yes" };

	private static final String[] FALSES = { "false", "0", "no" };

	public static <T> T[] convert(String[] objs, Class<T> type) throws Exception {
		if (objs == null) {
			return null;
		}

		if (String.class.equals(type)) {
			return (T[]) objs;
		}

		Class wrapper = ClassUtils.primitiveToWrapper(type);
		T[] result = (T[]) Array.newInstance(wrapper, objs.length);

		for (int i = 0; i < objs.length; i++) {
			result[i] = convert(objs[i], type);
		}

		return result;
	}

	public static <T> T convert(String str, Class<T> type) throws Exception {
		if (BaseUtils.isEmpty(str)) {
			return null;
		}
		if (String.class.equals(type)) {
			return (T) str;
		}
		if (Date.class.equals(type)) {
			return (T) str2Date(str.trim());
		}
		if (Boolean.class.equals(type) || boolean.class.equals(type)) {
			return (T) str2Boolean(str.trim());
		}
		return (T) str2Numer(str.trim(), type);
	}

	private static Date str2Date(String dateStr) throws Exception {
		if (PATTERN_YMD.matcher(dateStr).matches()) {
			return new SimpleDateFormat(Consts.YMD).parse(dateStr);
		}
		if (PATTERN_YMD_HMS.matcher(dateStr).matches()) {
			return new SimpleDateFormat(Consts.YMD_HMS).parse(dateStr);
		}
		if (PATTERN_HMS.matcher(dateStr).matches()) {
			return new SimpleDateFormat(Consts.HMS).parse(dateStr);
		}
		throw new RuntimeException();
	}

	private static Number str2Numer(String str, Class clazz) {
		Class wrapper = ClassUtils.primitiveToWrapper(clazz);
		if (Integer.class.equals(wrapper)) {
			return Integer.valueOf(str);
		}
		if (Double.class.equals(wrapper)) {
			return Double.valueOf(str);
		}
		if (Long.class.equals(wrapper)) {
			return Long.valueOf(str);
		}
		if (Float.class.equals(wrapper)) {
			return Float.valueOf(str);
		}
		if (Short.class.equals(wrapper)) {
			return Short.valueOf(str);
		}
		if (BigDecimal.class.equals(wrapper)) {
			return new BigDecimal(str);
		}
		if (BigInteger.class.equals(wrapper)) {
			return new BigInteger(str);
		}
		throw new RuntimeException();
	}

	private static Boolean str2Boolean(String str) {
		String str_lower = str.toLowerCase();
		if (ArrayUtils.contains(TRUES, str_lower)) {
			return true;
		}
		if (ArrayUtils.contains(FALSES, str_lower)) {
			return false;
		}
		throw new RuntimeException();
	}

}
