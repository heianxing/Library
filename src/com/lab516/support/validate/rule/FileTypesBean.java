package com.lab516.support.validate.rule;

import java.lang.annotation.Annotation;

import com.lab516.base.BaseUtils;
import com.lab516.base.Consts;
import com.lab516.support.validate.annotation.rule.FileTypes;

public class FileTypesBean extends Rule {

	public String getValidateRule(Annotation ruleAnno) {
		String ruleName = FileTypes.class.getSimpleName();
		String FileTypess = ((FileTypes) ruleAnno).value();
		return ruleName + Consts.L_BRACE + FileTypess + Consts.R_BRACE;
	}

	@Override
	public boolean validate(Object... params) {
		String fileName = (String) params[0];

		if (BaseUtils.isEmpty(fileName)) {
			return true;
		}

		fileName = fileName.toLowerCase();
		String[] FileTypesArr = params[1].toString().toLowerCase().split("\\|");
		for (String FileTypes : FileTypesArr) {
			String fileSuffix = "." + FileTypes;
			if (fileName.endsWith(fileSuffix)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDefaultMsg(Annotation ruleAnno) {
		String FileTypess = ((FileTypes) ruleAnno).value();
		return "只能是以下格式:" + FileTypess.replaceAll("\\|", "，");
	}

}
