package com.lab516.support.conveter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.lab516.base.BaseEntity;
import com.lab516.support.request.EhWebRequest;
import com.lab516.support.validate.EntityValidateRuleResolver;
import com.lab516.support.validate.FieldValidateRule;

public class EntityConverter {

	public static <T> T converterOne(Class<T> entityClazz, EhWebRequest request) throws Exception {
		return converterSome(entityClazz, request)[0];
	}

	public static <T> T[] converterSome(Class<T> entityClazz, EhWebRequest request) throws Exception {
		List<T> entityList = new ArrayList<T>();
		Field[] fields = entityClazz.getDeclaredFields();

		for (Field field : fields) {
			String fieldName = field.getName();
			Class fieldType = field.getType();

			Object[] fieldVals = request.getParameterValues(fieldName, fieldType);

			if (ArrayUtils.isEmpty(fieldVals)) {
				continue;
			}

			for (int index = 0; index < fieldVals.length; index++) {
				initIndexInEntityList(entityList, entityClazz, index);
				BaseEntity entity = (BaseEntity) entityList.get(index);

				Object fieldVal = fieldVals[index];

				if (fieldVal != null) {
					validateFieldValue(entity, fieldName, fieldVal, request);

					BeanUtils.setProperty(entity, fieldName, fieldVal);
				}
			}
		}

		T[] t = (T[]) Array.newInstance(entityClazz, 0);
		return entityList.toArray(t);
	}

	private static void initIndexInEntityList(List entityList, Class entityClazz, int index) throws Exception {
		if (entityList.size() - 1 < index) {
			entityList.add((BaseEntity) entityClazz.newInstance());
		}
	}

	private static void validateFieldValue(BaseEntity entity, String fieldName, Object fieldVal, EhWebRequest request) {
		FieldValidateRule rule = EntityValidateRuleResolver.getRuleByClassAndFieldName(entity.getClass(), fieldName);
		if (rule != null && !rule.validate(fieldVal)) {
			System.out.println("error fieldName:" + fieldName + " fieldVal:" + fieldVal);
			System.out.println("rule:" + rule.getValidateRule());
			request.addBindError(rule.getErrorTip());
		}
	}

}
