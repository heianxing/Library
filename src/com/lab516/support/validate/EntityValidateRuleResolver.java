package com.lab516.support.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.lab516.base.BaseUtils;
import com.lab516.base.Consts;
import com.lab516.support.validate.annotation.ErrorMsg;
import com.lab516.support.validate.annotation.FieldTipName;
import com.lab516.support.validate.rule.Rule;

public class EntityValidateRuleResolver {

	// Map<实体类, 实体所有的字段校验信息>
	private static Map<Class, Map<String, FieldValidateRule>> classValidateRulesMap = new ConcurrentHashMap<>();

	public static FieldValidateRule getRuleByClassAndFieldName(Class clazz, String fieldName) {
		return getRuleMapByClass(clazz).get(fieldName);
	}

	public static Collection<FieldValidateRule> getRulesByClass(Class clazz) {
		return getRuleMapByClass(clazz).values();
	}

	public static Map<String, FieldValidateRule> getRuleMapByClass(Class clazz) {
		if (!classValidateRulesMap.containsKey(clazz)) {
			Map<String, FieldValidateRule> ruleMap = new HashMap<>();

			for (Field field : clazz.getDeclaredFields()) {
				String fieldTipName = getFieldTipName(field);

				// 字段中文名为空则不用校验
				if (BaseUtils.isEmpty(fieldTipName)) {
					continue;
				}

				String fieldName = field.getName();
				String validateRule = getValidateRules(field);
				String errorMsg = getErrorMsg(field);
				Class fieldType = field.getType();

				FieldValidateRule fieldValidateRule = new FieldValidateRule(fieldName, fieldTipName, fieldType,
						validateRule, errorMsg);

				ruleMap.put(fieldName, fieldValidateRule);
			}

			classValidateRulesMap.put(clazz, ruleMap);
		}

		return classValidateRulesMap.get(clazz);
	}

	private static String getFieldTipName(Field field) {
		FieldTipName nameAnno = field.getAnnotation(FieldTipName.class);
		return (nameAnno != null) ? nameAnno.value() : null;
	}

	private static String getErrorMsg(Field field) {
		String annoErrMsg = getAnnotationErrorMsg(field);
		return (annoErrMsg != null) ? annoErrMsg : getDefaultErrorMsg(field);
	}

	private static String getAnnotationErrorMsg(Field field) {
		ErrorMsg errorMsgAnno = field.getAnnotation(ErrorMsg.class);
		return (errorMsgAnno != null) ? errorMsgAnno.value() : null;
	}

	private static String getDefaultErrorMsg(Field field) {
		StringBuilder errorMsgs = new StringBuilder();
		for (Annotation anno : field.getAnnotations()) {
			if (Rule.isRuleAnnotation(anno)) {
				errorMsgs.append(Rule.getDefaultMsgByAnnotion(anno) + "，"); // Consts.SBC_SPLIT
			}
		}
		return StringUtils.removeEnd(errorMsgs.toString(), "，");
	}

	private static String getValidateRules(Field field) {
		StringBuilder rules = new StringBuilder();
		for (Annotation anno : field.getAnnotations()) {
			if (Rule.isRuleAnnotation(anno)) {
				rules.append(Rule.getValidateRuleByAnnotion(anno) + Consts.SBC_SPLIT);
			}
		}
		return StringUtils.removeEnd(rules.toString(), Consts.SBC_SPLIT);
	}

}
