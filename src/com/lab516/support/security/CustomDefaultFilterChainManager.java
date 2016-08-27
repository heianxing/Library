package com.lab516.support.security;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;

import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.Nameable;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.apache.shiro.web.filter.mgt.SimpleNamedFilterList;

public class CustomDefaultFilterChainManager extends DefaultFilterChainManager {

	private String loginUrl;

	private String successUrl;

	private String unauthorizedUrl;

	// 调用其构造器时，会自动注册默认的拦截器；
	public CustomDefaultFilterChainManager() {
		setFilters(new LinkedHashMap<String, Filter>());
		setFilterChains(new LinkedHashMap<String, NamedFilterList>());
		addDefaultFilters(false);
	}

	public void setKickoutFilter(KickoutSessionControlFilter kickoutFilter) {
		addFilter("kickout", kickoutFilter);
	}

	// 初始化方法， 给拦截器设置相关属性，构造默认的拦截器链
	@PostConstruct
	public void init() {
		// 设置拦截器的相关属性
		Map<String, Filter> filters = getFilters();
		if (!CollectionUtils.isEmpty(filters)) {
			for (Map.Entry<String, Filter> entry : filters.entrySet()) {
				String name = entry.getKey();
				Filter filter = entry.getValue();

				applyGlobalPropertiesIfNecessary(filter);

				if (filter instanceof Nameable) {
					((Nameable) filter).setName(name);
				}
			}
		}
	}

	// 给拦截器设置
	private void applyGlobalPropertiesIfNecessary(Filter filter) {
		if (filter instanceof AccessControlFilter) {
			((AccessControlFilter) filter).setLoginUrl(loginUrl);
		}
		if (filter instanceof AuthenticationFilter) {
			((AuthenticationFilter) filter).setSuccessUrl(successUrl);
		}
		if (filter instanceof AuthorizationFilter) {
			((AuthorizationFilter) filter).setUnauthorizedUrl(unauthorizedUrl);
		}
	}

	// 组合多个拦截器链为一个生成一个新的代理
	public FilterChain proxy(FilterChain original, List<String> chainNames) {
		NamedFilterList configured = new SimpleNamedFilterList(chainNames.toString());
		
		for (String chainName : chainNames) {
			configured.addAll(getChain(chainName));
		}
		
		return configured.proxy(original);
	}

	@Override
	protected void initFilter(Filter filter) {
		// 此处不做任何工作，因为交给spring管理了，所以Filter的相关配置会在Spring配置中完成；
	}

	// getter and setter ...

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}

}
