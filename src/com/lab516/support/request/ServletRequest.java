package com.lab516.support.request;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class ServletRequest {

	protected HttpServletRequest request;

	public ServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getHttpServletRequest() {
		return request;
	}

	public int getContentLength() {
		return request.getContentLength();
	}

	public String getContentType() {
		return request.getContentType();
	}

	public Object getAttribute(String arg0) {
		return request.getAttribute(arg0);
	}

	public Enumeration<String> getAttributeNames() {
		return request.getAttributeNames();
	}

	public void removeAttribute(String arg0) {
		request.removeAttribute(arg0);
	}

	public void setAttribute(String arg0, Object arg1) {
		request.setAttribute(arg0, arg1);
	}

	public Enumeration<String> getParameterNames() {
		return request.getParameterNames();
	}

	public String[] getParameterValues(String arg0) {
		return request.getParameterValues(arg0);
	}

	public ServletContext getServletContext() {
		return request.getSession().getServletContext();
	}

	public String getContextPath() {
		return request.getContextPath();
	}

	public HttpSession getSession() {
		return request.getSession();
	}

}
