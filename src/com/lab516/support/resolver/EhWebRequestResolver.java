package com.lab516.support.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.lab516.support.request.EhWebRequest;

public class EhWebRequestResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return EhWebRequest.class.equals(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
			NativeWebRequest nativeRequest, WebDataBinderFactory bindFactory) throws Exception {

		return new EhWebRequest(container, nativeRequest);
	}

}