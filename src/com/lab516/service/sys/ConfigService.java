package com.lab516.service.sys;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lab516.base.BaseService;
import com.lab516.base.Consts;
import com.lab516.base.JPAQuery;
import com.lab516.base.Page;
import com.lab516.entity.sys.Config;

@Service
public class ConfigService extends BaseService<Config> {

	private final String CACHE = "configCache";

	@Transactional
	@CacheEvict(value = CACHE, allEntries = true)
	public Config insert(Config config) {
		return super.insert(config);
	}

	@Transactional
	@CacheEvict(value = CACHE, allEntries = true)
	public Config update(Config config) {
		return super.update(config);
	}

	@Transactional
	@CacheEvict(value = CACHE, allEntries = true)
	public void delete(Serializable id) {
		super.delete(id);
	}

	@Cacheable(value = CACHE)
	public String findValueByName(String cfg_name) {
		String hql = "from Config where cfg_name = :cfg_name";
		Query query = em.createQuery(hql);
		query.setParameter("cfg_name", cfg_name);
		List<Config> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0).getCfg_value();
	}

	public String findApkUrl() {
		return findValueByName(Consts.CFG_NAME_APK_URL);
	}

	public String findApkVesion() {
		return findValueByName(Consts.CFG_NAME_APK_VER);
	}

	public String findTipMessage() {
		return findValueByName(Consts.CFG_TIP_MESSAGE);
	}

	public Page findPage(int page_no, int page_size, String cfg_id, String cfg_name, String cfg_value) {
		JPAQuery query = createJPAQuery();
		query.whereNullableContains("cfg_id", cfg_id);
		query.whereNullableContains("cfg_name", cfg_name);
		query.whereNullableContains("cfg_value", cfg_value);
		return query.getPage(page_no, page_size);
	}

}
