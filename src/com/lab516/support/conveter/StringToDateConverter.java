package com.lab516.support.conveter;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String str) {
		try {
			return StringConverter.convert(str, Date.class);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

}
