package com.lab516.support.validate.rule;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.ArrayUtils;

import com.lab516.base.BaseUtils;
import com.lab516.support.validate.annotation.rule.Require;

public class RequireBean extends Rule {

	public String getValidateRule(Annotation ruleAnno) {
		return Require.class.getSimpleName();
	}

	public boolean validate(Object... params) {
		Object fieldVal = params[0];

		if (fieldVal == null) {
			return false;
		}

		Class clazz = fieldVal.getClass();
		if (String.class.equals(clazz)) {
			return !BaseUtils.isEmpty(fieldVal.toString());
		}

		if (clazz.isArray()) {
			long len = ArrayUtils.getLength(fieldVal);
			return (len > 0);
		}
		
		return true;
	}

	public String getDefaultMsg(Annotation ruleAnno) {
		return "不能为空";
	}

}
