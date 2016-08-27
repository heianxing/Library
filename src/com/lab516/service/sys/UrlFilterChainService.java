package com.lab516.service.sys;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lab516.base.BaseService;
import com.lab516.base.JPAQuery;
import com.lab516.base.Page;
import com.lab516.entity.sys.UrlFilterChain;
import com.lab516.support.security.ShiroFilerChainManager;

/*
 * 如果filter发生了变化一定要通知shiro, 即调用initFilterChain()方法
 */
@Service
public class UrlFilterChainService extends BaseService<UrlFilterChain> {

	@Autowired
	private ShiroFilerChainManager shiroFilerChainManager;

	@Transactional
	public UrlFilterChain insert(UrlFilterChain ufc) {
		super.insert(ufc);
		initFilterChain();
		return ufc;
	}

	@Transactional
	public UrlFilterChain update(UrlFilterChain ufc) {
		em.merge(ufc);
		initFilterChain();
		return ufc;
	}

	@Transactional
	public void delete(String id) {
		UrlFilterChain urlFilter = em.find(UrlFilterChain.class, id);
		em.remove(urlFilter);
		initFilterChain();
	}

	public Page findPage(int page_no, int page_size, String filt_name,
			String filt_url, String filt_chain) {

		JPAQuery query = createJPAQuery();
		query.whereNullableContains("filt_name", filt_name);
		query.whereNullableContains("filt_url", filt_url);
		query.whereNullableContains("filt_chain", filt_chain);
		return query.getPage(page_no, page_size);
	}

	@PostConstruct
	public void initFilterChain() {
		List<UrlFilterChain> allUfc = em.createQuery(
				"from UrlFilterChain where enable = true").getResultList();
		shiroFilerChainManager.initFilterChains(allUfc);
	}

}
