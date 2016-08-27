package com.lab516.support.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.lab516.support.map.EhModelMap;

public class EhModelMapResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return EhModelMap.class.equals(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter param, ModelAndViewContainer container, NativeWebRequest req,
			WebDataBinderFactory factory) throws Exception {

		return new EhModelMap(container.getModel());
	}

}