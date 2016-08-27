package com.lab516.support.security;

import java.util.List;

import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab516.entity.sys.UrlFilterChain;

/*
 * 注册URL-拦截器的映射关系
 */
@Service
public class ShiroFilerChainManager {

	@Autowired
	private DefaultFilterChainManager filterChainManager;

	// 在进行增删改UrlFilter后，把拦截器重新注册到Shiro
	public void initFilterChains(List<UrlFilterChain> urlFilterChains) {
		filterChainManager.getFilterChains().clear();

		for (UrlFilterChain ufc : urlFilterChains) {
			filterChainManager.createChain(ufc.getFilt_url(), ufc.getFilt_chain());
		}
	}

}