package com.lab516.support.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.lab516.base.BaseEntity;
import com.lab516.support.conveter.EntityConverter;
import com.lab516.support.request.EhWebRequest;

public class EntityArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return BaseEntity.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
			NativeWebRequest nativeRequest, WebDataBinderFactory bindFactory) throws Exception {
		
		EhWebRequest req = new EhWebRequest(container, nativeRequest);
		Class entityClass = parameter.getParameterType();
		return EntityConverter.converterOne(entityClass, req);
	}

}