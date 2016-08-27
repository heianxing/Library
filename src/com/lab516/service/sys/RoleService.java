package com.lab516.service.sys;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.lab516.base.BaseService;
import com.lab516.base.JPAQuery;
import com.lab516.base.Page;
import com.lab516.entity.sys.Role;

@Service
public class RoleService extends BaseService<Role> {

	public Set<Role> findByIds(String[] rolesIds) {
		String hql = "from Role where role_id in (:rolesIds)";
		Query query = em.createQuery(hql);
		query.setParameter("rolesIds", Arrays.asList(rolesIds));
		List<Role> list = query.getResultList();
		return new HashSet(list);
	}

	public Page findPage(int page_no, int page_size, String role_id, String role_name) {
		JPAQuery query = createJPAQuery();
		query.whereNullableContains("role_id", role_id);
		query.whereNullableContains("role_name", role_name);
		return query.getPage(page_no, page_size);
	}

}
