package com.lab516.support.validate.rule;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.lab516.base.Consts;
import com.lab516.support.validate.annotation.rule.NumFormat;

public class NumFormatBean extends Rule {

	private static final Map<String, Pattern> patternMap = new HashMap<String, Pattern>();

	/** 验证数字格式的正则，需要填充参数 */
	private static final String RAW_REGEX = "^[-+]?\\d{1,%d}(\\.\\d{0,%d})?$";

	public String getValidateRule(Annotation ruleAnno) {
		String ruleName = NumFormat.class.getSimpleName();
		NumFormat numAnno = ((NumFormat) ruleAnno);
		return ruleName + Consts.L_BRACE + numAnno.posLen() + Consts.DBC_SPLIT
				+ numAnno.negLen() + Consts.R_BRACE;
	}

	public boolean validate(Object... params) {
		String fieldVal = params[0].toString();
		if (fieldVal == null)
			return true;

		int posLen = Integer.parseInt(params[1].toString());
		int negLen = Integer.parseInt(params[2].toString());
		Pattern pattern = getPattern(posLen, negLen);
		return pattern.matcher(fieldVal).matches();
	}

	public String getDefaultMsg(Annotation ruleAnno) {
		NumFormat numAnno = ((NumFormat) ruleAnno);
		if (numAnno.negLen() != 0) {
			return "整数位数不能超过" + numAnno.posLen() + ",小数位数不能超过"
					+ numAnno.negLen();
		} else {
			return "必须是不超过" + numAnno.posLen() + "位的整数";
		}
	}

	private static Pattern getPattern(int posLen, int negLen) {
		String regex = String.format(RAW_REGEX, posLen, negLen);
		Pattern pattern = patternMap.get(regex);
		if (pattern == null) {
			pattern = Pattern.compile(regex);
			patternMap.put(regex, pattern);
		}
		return pattern;
	}

}
