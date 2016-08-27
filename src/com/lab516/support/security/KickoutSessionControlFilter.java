package com.lab516.support.security;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.lab516.base.Consts;
import com.lab516.entity.sys.User;

public class KickoutSessionControlFilter extends AccessControlFilter {

	private String kickoutUrl;

	private String homeUrl;

	private Map<String, String> cache = new ConcurrentHashMap<>();

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {

		HttpServletRequest httpReq = (HttpServletRequest) request;
		String url = httpReq.getRequestURI();

		Subject subject = getSubject(request, response);
		Session session = subject.getSession();
		User user = (User) subject.getPrincipal();

		if (session == null || user == null) {
			return true;
		}

		String userId = user.getUser_id();
		String sessionId = (String) session.getId();

		if (url.endsWith(homeUrl)) {
			cache.put(userId, sessionId);
		} else {
			String onlineSessionId = cache.get(userId);
			if (onlineSessionId != null && !sessionId.equals(onlineSessionId)) {
				subject.logout();
				saveRequest(request);
				httpReq.getSession().setAttribute(Consts.KICKOUT_MSG, "您的账号已在别处登陆，如非本人操作，请登陆后修改密码。");
				WebUtils.issueRedirect(request, response, kickoutUrl);
				return false;
			}
		}

		return true;
	}

	public void setKickoutUrl(String kickoutUrl) {
		this.kickoutUrl = kickoutUrl;
	}

	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}

}
