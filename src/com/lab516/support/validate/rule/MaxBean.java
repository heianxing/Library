package com.lab516.support.validate.rule;

import java.lang.annotation.Annotation;

import com.lab516.base.Consts;
import com.lab516.support.validate.annotation.rule.Max;

public class MaxBean extends Rule {

	private static final String REGEX = "\\.0*$";

	public String getValidateRule(Annotation ruleAnno) {
		String ruleName = Max.class.getSimpleName();
		double maxVal = ((Max) ruleAnno).value();
		return ruleName + Consts.L_BRACE + maxVal + Consts.R_BRACE;
	}

	@Override
	public boolean validate(Object... params) {
		String fieldValStr = params[0].toString();

		if (fieldValStr == null) {
			return true;
		}

		double maxVal = Double.parseDouble(params[1].toString());
		double fieldVal = Double.parseDouble(fieldValStr);
		return (fieldVal <= maxVal);
	}

	@Override
	public String getDefaultMsg(Annotation ruleAnno) {
		double maxVal = ((Max) ruleAnno).value();
		// 把replaceFirst是为了把"1000.00"变成"1000" (只针对小数点后全是0的情况)
		String maxValStr = String.valueOf(maxVal).replaceFirst(REGEX, "");
		return "必须要小于" + maxValStr;
	}

}
