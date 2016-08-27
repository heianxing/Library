package com.lab516.support.validate.rule;

import java.lang.annotation.Annotation;
import java.text.DecimalFormat;

import org.apache.commons.lang3.ArrayUtils;

import com.lab516.base.Consts;
import com.lab516.support.validate.annotation.rule.FileMaxSize;

public class FileMaxSizeBean extends Rule {

	private static final String NUM_FORMAT = "###,###,###,###,###";

	public String getValidateRule(Annotation ruleAnno) {
		String ruleName = FileMaxSize.class.getSimpleName();
		long maxSize = ((FileMaxSize) ruleAnno).value();
		return ruleName + Consts.L_BRACE + maxSize + Consts.R_BRACE;
	}

	@Override
	public boolean validate(Object... params) {
		Object fieldVal = params[0];

		if (fieldVal == null) {
			return true;
		}

		long maxSize = Long.parseLong(params[1].toString());
		
		// fieldVal is byte[] or Byte[]
		long fileSize = ArrayUtils.getLength(fieldVal);
		return (fileSize <= maxSize);
	}

	@Override
	public String getDefaultMsg(Annotation ruleAnno) {
		long maxSize = ((FileMaxSize) ruleAnno).value();
		String maxByte = new DecimalFormat(NUM_FORMAT).format(maxSize / 8);
		return "大小不能超过" + maxByte + "字节";
	}

}
