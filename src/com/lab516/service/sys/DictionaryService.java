package com.lab516.service.sys;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lab516.base.BaseService;
import com.lab516.base.JPAQuery;
import com.lab516.base.Page;
import com.lab516.entity.sys.Dictionary;

@Service
public class DictionaryService extends BaseService<Dictionary> {

	private final String CACHE = "dictionaryCache";

	@Transactional
	@CacheEvict(value = CACHE, allEntries = true)
	public Dictionary insert(Dictionary dict) {
		return super.insert(dict);
	}

	@Transactional
	@CacheEvict(value = CACHE, allEntries = true)
	public Dictionary update(Dictionary dict) {
		return super.update(dict);
	}

	@Transactional
	@CacheEvict(value = CACHE, allEntries = true)
	public void delete(Serializable id) {
		super.delete(id);
	}

	@Cacheable(value = CACHE)
	public List<Dictionary> findByType(String dict_type) {
		String hql = "from Dictionary D where D.dict_type = :dict_type order by D.order_no";
		Query query = em.createQuery(hql);
		query.setParameter("dict_type", dict_type);
		return query.getResultList();
	}

	public Page findPage(int page_no, int page_size, String dict_name,
			String dict_type) {

		JPAQuery query = createJPAQuery();
		query.whereNullableContains("dict_name", dict_name);
		query.whereNullableContains("dict_type", dict_type);
		query.orderByDesc("dict_type");
		query.orderByAsc("order_no");
		return query.getPage(page_no, page_size);
	}

}
