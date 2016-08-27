package com.lab516.service.sys;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lab516.base.BaseService;
import com.lab516.base.Consts;
import com.lab516.base.JPAQuery;
import com.lab516.base.Page;
import com.lab516.entity.sys.Menu;
import com.lab516.entity.sys.Role;

@Service
public class MenuService extends BaseService<Menu> {

	@Autowired
	private RoleService roleService;

	/**
	 * 通过用户角色获取其可以访问菜单
	 * <p>
	 * 一个menu的子菜单在其subMenus属性中<br>
	 * 返回结果有点像一个tree, 深度为1的节点是一个list
	 *
	 * @param roles_id
	 * @return
	 */
	public List<Menu> findTreeByRolesId(String[] roles_id) {
		String hql = "select distinct(M) " + "	  from Menu M left join fetch M.roles R "
				+ "	  where R.role_id in (:roles_id) " + "	  order by M.order_no * 1 ";
		Query query = em.createQuery(hql);
		query.setParameter("roles_id", Arrays.asList(roles_id));
		List<Menu> menuList = query.getResultList();
		buildTree(menuList);
		return menuList;
	}

	/** 把list构造成一个类似tree的结构 */
	private void buildTree(List<Menu> menuList) {
		// 为menu找其上级菜单
		for (Menu menu : menuList) {
			for (Menu father : menuList) {
				// 如果menu的上级菜单是father，则把menu添加到father的下级菜单中
				if (menu.isSubMenuOf(father)) {
					father.addSubMenus(menu);
					break;
				}
			}
		}

		// 在list只保留最高一级的菜单, 其余的删去
		for (Iterator iterator = menuList.iterator(); iterator.hasNext();) {
			Menu menu = (Menu) iterator.next();

			// 最高一级的菜单的上级菜单是Consts.NONE
			if (!Consts.NONE.equals(menu.getParent_id())) {
				iterator.remove();
			}
		}
	}

	/**
	 * 查找可供选择的上级菜单
	 * <p>
	 * 第一级或者第二级菜单
	 */
	public List<Menu> findParentCandidates() {
		String sql = "select t01.* 				  "
				+ "	  from   s_menu t01 left join s_menu t02 on t01.parent_id = t02.menu_id"
				+ "   where  t02.parent_id = '-1' " // 第一级菜单:其parent_id是"-1"
				+ "	  or 	 t01.parent_id = '-1' "; // 第二级菜单:其上级菜单的parent_id是"-1"
		Query query = em.createNativeQuery(sql, Menu.class);
		return query.getResultList();
	}

	/**
	 * 查找可供选择的上级菜单
	 * <p>
	 * 第一级或者第二级菜单并且不能是它自己
	 *
	 * @see findParentCandidates()
	 * @param menu_id
	 * @return
	 */
	public List<Menu> findParentCandidates(String menu_id) {
		String sql = "select t01.* 					"
				+ "	  from   s_menu t01 left join s_menu t02 on t01.parent_id = t02.menu_id"
				+ "   where  ( t02.parent_id = '-1' or t01.parent_id = '-1' ) " // 一二级菜单
				+ "   and    t01.menu_id != :menu_id "; // 不能是它自己
		Query query = em.createNativeQuery(sql, Menu.class);
		query.setParameter("menu_id", menu_id);
		return query.getResultList();
	}

	public Page findPage(int _pageNo, int pageSize, String menu_name, String menu_url) {
		long recordCount = findCount(menu_name, menu_url);
		int pageCount = calcPageCount(pageSize, recordCount);
		int pageNo = Math.min(pageCount, _pageNo);
		int start = calcStart(pageNo, pageSize);

		// 把菜单所属的角色也查出来
		StringBuilder hql = new StringBuilder("  		   select distinct(M) 						"
				+ "from Menu M left join fetch M.roles 		" + "where 1=1 ");
		if (!isEmpty(menu_name)) {
			hql.append("   and   M.menu_name like :menu_name ");
		}
		if (!isEmpty(menu_url)) {
			hql.append("   and   M.menu_url  like :menu_url ");
		}
		hql.append("       order by M.order_no * 1 ");

		Query query = em.createQuery(hql.toString());

		if (!isEmpty(menu_name)) {
			query.setParameter("menu_name", "%" + menu_name + "%");
		}
		if (!isEmpty(menu_url)) {
			query.setParameter("menu_url", "%" + menu_url + "%");
		}

		query.setFirstResult(start);
		query.setMaxResults(pageSize);

		List<Menu> recordList = query.getResultList();
		return new Page(recordList, recordCount, pageSize, pageNo, pageCount);
	}

	private long findCount(String menu_name, String menu_url) {
		JPAQuery query = createJPAQuery();
		query.whereNullableContains("menu_name", menu_name);
		query.whereNullableContains("menu_url", menu_url);
		return query.getRecordCount();
	}

	@Transactional
	public void addMenusRole(String menu_id, String role_id) {
		Menu menu = findWithRoles(menu_id);
		Role role = roleService.findById(role_id);
		menu.getRoles().add(role);
		em.merge(menu);
	}

	@Transactional
	public void removeMenusRole(String menu_id, String role_id) {
		Menu menu = findWithRoles(menu_id);
		Role role = roleService.findById(role_id);
		menu.getRoles().remove(role);
		em.merge(menu);
	}

	/** 查询菜单及其所属角色 */
	public Menu findWithRoles(String menu_id) {
		String hql = "from Menu M left join fetch M.roles where M.menu_id = :menu_id ";
		Query query = em.createQuery(hql);
		query.setParameter("menu_id", menu_id);
		List<Menu> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0);
	}

	@Transactional
	public Menu insert(Menu menu) {
		String[] rolesIds = menu.getRoles_id().split(Consts.DBC_SPLIT);
		Set<Role> roles = roleService.findByIds(rolesIds);
		menu.setRoles(roles);
		super.insert(menu);
		return menu;
	}

	@Transactional
	public Menu update(Menu menu) {
		String[] rolesIds = menu.getRoles_id().split(Consts.DBC_SPLIT);
		Set<Role> roles = roleService.findByIds(rolesIds);
		menu.setRoles(roles);
		super.update(menu);
		return menu;
	}

	@Transactional
	public void deleteInvalid() {
		String sql = "delete from s_role_menu where role_id not in (select role_id from s_role)";
		em.createNativeQuery(sql).executeUpdate();
	}

}
