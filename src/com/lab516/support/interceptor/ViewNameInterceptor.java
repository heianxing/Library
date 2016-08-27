package com.lab516.support.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lab516.base.BaseController;

public class ViewNameInterceptor implements HandlerInterceptor {

	private static final String BASE_PATH = "com.lab516.web.";

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		if (modelAndView == null || !modelAndView.hasView()) {
			return;
		}

		String viewName = modelAndView.getViewName();
		if (BaseController.DFT_FTL.equals(viewName)) {
			viewName = getControllerPath((HandlerMethod) handler) + ".ftl";
		}
		if (BaseController.FM_FTL.equals(viewName)) {
			viewName = getControllerPath((HandlerMethod) handler).replaceFirst("(Add|Edit)$", "Form.ftl");
		}

		System.out.println("ViewName:" + viewName);
		modelAndView.setViewName(viewName);
	}

	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		return true;
	}

	private String getControllerPath(HandlerMethod handler) {
		String ctrlName = ((HandlerMethod) handler).getBean().getClass().getName();
		return ctrlName.replaceFirst(BASE_PATH, "").replaceAll("\\.", "/");
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
