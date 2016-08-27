package com.lab516.support.validate;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.lab516.base.Consts;
import com.lab516.support.validate.rule.Rule;

public class FieldValidateRule {

	/** 字段英文名 */
	private String fieldName;
	/** 字段中文名 */
	private String fieldTipName;
	/** 字段类型 */
	private Class fieldType;
	/** 字段校验规则 */
	private String validateRule;
	/** 字段校验错误 */
	private String errorMsg;

	public FieldValidateRule(String fieldName) {
		this.fieldName = fieldName;
	}

	public FieldValidateRule(String fieldName, String fieldTipName, Class fieldType, String validateRule,
			String errorMsg) {
		this.fieldName = fieldName;
		this.fieldTipName = fieldTipName;
		this.fieldType = fieldType;
		this.validateRule = validateRule;
		this.errorMsg = errorMsg;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldTipName() {
		return fieldTipName;
	}

	public void setFieldTipName(String fieldTipName) {
		this.fieldTipName = fieldTipName;
	}

	public String getValidateRule() {
		return validateRule;
	}

	public void setValidateRule(String validateRule) {
		this.validateRule = validateRule;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Class getFieldType() {
		return fieldType;
	}

	public void setFieldType(Class fieldType) {
		this.fieldType = fieldType;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * 验证字段的值是否符合校验规则
	 * 
	 * @param fieldValue
	 *            字段值
	 * @return
	 */
	public boolean validate(Object fieldValue) {
		String[] rules = validateRule.split(Consts.SBC_SPLIT);

		for (String rule : rules) {
			if (!Rule.validate(rule, fieldValue)) {
				return false;
			}
		}
		return true;
	}

	public String getErrorTip() {
		return this.fieldTipName + this.errorMsg;
	}

}
