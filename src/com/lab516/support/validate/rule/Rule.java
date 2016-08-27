package com.lab516.support.validate.rule;

import java.lang.annotation.Annotation;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang3.StringUtils;

import com.lab516.base.BaseUtils;
import com.lab516.base.Consts;

public abstract class Rule {

	/** 验证方法名称 */
	private static final String VALIDATE_METHOD = "validate";

	private static final String RULES_ANNO_PACKAGE = "com.lab516.support.validate.annotation.rule";

	private static final String RULE_BEAN_PACKAGE = "com.lab516.support.validate.rule";

	private static final String RULE_BEAN_PREFIX = "Bean";

	/** 从注解获得带参数验证规则字符串 如:Max(1) Require 之类 */
	public abstract String getValidateRule(Annotation ruleAnno);

	/** 验证表单数据是否正确 */
	public abstract boolean validate(Object... params);

	/** 获得验证规则的默认信息 */
	public abstract String getDefaultMsg(Annotation ruleAnno);

	/** 获取验证注解的默认提示信息 **/
	public static String getDefaultMsgByAnnotion(Annotation anno) {
		return createRuleByName(anno.annotationType().getSimpleName())
				.getDefaultMsg(anno);
	}

	/** 获取验证规则通过验证注解 **/
	public static String getValidateRuleByAnnotion(Annotation anno) {
		return createRuleByName(anno.annotationType().getSimpleName())
				.getValidateRule(anno);
	}

	/** 是否是一个验证规则注解 */
	public static boolean isRuleAnnotation(Annotation anno) {
		String pack = anno.annotationType().getPackage().getName();
		return RULES_ANNO_PACKAGE.equals(pack);
	}

	/** 创建一个验证规则类通过验证规则字符串 **/
	private static Rule createRuleByName(String rule) {
		try {
			Class ruleInfoClazz = Class.forName(RULE_BEAN_PACKAGE + "."
					+ getRuleName(rule) + RULE_BEAN_PREFIX);
			return (Rule) ruleInfoClazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/** 通过验证规则来验证数据 **/
	public static boolean validate(String rule, Object fieldValue) {
		String ruleName = getRuleName(rule);
		Object[] ruleParams = getRuleParams(fieldValue, rule);

		try {
			Object[] paramsObjs = { ruleParams };
			Class[] paramTypes = { Object[].class };

			return (Boolean) MethodUtils.invokeMethod(createRuleByName(rule),
					VALIDATE_METHOD, paramsObjs, paramTypes);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("rule:" + rule + " fieldValue:" + fieldValue); // TODO
			throw new RuntimeException();
		}
	}

	private static Object[] getRuleParams(Object filedValue, String rule) {
		String ruleParamsStr = BaseUtils.subStringBetween(rule, Consts.L_BRACE,
				Consts.R_BRACE);

		Object[] ruleParams = null;
		if (!BaseUtils.isEmpty(ruleParamsStr)) {
			ruleParams = ruleParamsStr.split(Consts.DBC_SPLIT);
		} else {
			ruleParams = new Object[] {};
		}

		// 组合filedValue和ruleParams成一个数组
		Object[] params = new Object[ruleParams.length + 1];
		params[0] = filedValue;
		System.arraycopy(ruleParams, 0, params, 1, ruleParams.length);
		return params;
	}

	private static String getRuleName(String rule) {
		return StringUtils.substringBefore(rule, Consts.L_BRACE);
	}

}
