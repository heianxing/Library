package com.lab516.service.sys;

import java.util.Date;

import javax.persistence.Query;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lab516.base.BaseService;
import com.lab516.base.JPAQuery;
import com.lab516.base.Page;
import com.lab516.entity.sys.User;
import com.lab516.support.exception.LoginTimeoutException;

@Service
public class UserService extends BaseService<User> {

	@Transactional
	public User add(User user) throws Exception {
		if (existByUserId(user.getUser_id())) {
			throw new RuntimeException("账号【" + user.getUser_id() + "】已被他人使用！");
		}

		user.setPassword(encrypt(user.getPassword()));
		user.setCreate_date(new Date());
		return insert(user);
	}

	public boolean existByUserId(String user_id) {
		String hql = "select count(*) from User where user_id = :user_id";
		Query query = em.createQuery(hql);
		query.setParameter("user_id", user_id);
		return (Long) query.getSingleResult() > 0;
	}

	/** 获取当前用户 */
	public static User currentUser() {
		User user = (User) SecurityUtils.getSubject().getPrincipal();

		if (user == null) {
			throw new LoginTimeoutException();
		}

		return user;
	}

	public static String currentUserId() {
		return currentUser().getUser_id();
	}

	private String encrypt(String str) {
		return new Md5Hash(str, str, 2).toString();
	}

	@Transactional
	public void changePassword(String user_id, String old_pwd, String new_pwd) throws Exception {
		User user = findById(user_id);

		if (!user.getPassword().equals(encrypt(old_pwd))) {
			throw new RuntimeException("密码错误!");
		}

		changePassword(user_id, new_pwd);
	}

	@Transactional
	public void changePassword(String user_id, String password) {
		String hql = "update User set password = :password where user_id = :user_id";
		Query query = em.createQuery(hql);
		query.setParameter("password", encrypt(password));
		query.setParameter("user_id", user_id);
		query.executeUpdate();
	}

	public User login(String userId, String password) throws Exception {
		User user = findById(userId);
		if (user == null) {
			throw new RuntimeException("账号错误！");
		}
		if (!user.getPassword().equals(encrypt(password))) {
			throw new RuntimeException("密码错误！");
		}
		if (!user.isEnable()) {
			throw new AuthenticationException("该账户已被禁用！");
		}
		return user;
	}

	/**
	 * 修改用户禁启用状态
	 * 
	 * @param user_id
	 * @param enable
	 */
	@Transactional
	public void changeEnable(String user_id, boolean enable) {
		User user = findById(user_id);
		user.setEnable(enable);
		update(user);
	}

	public Page findPage(int page_no, int page_size, String user_id, String user_name, String role_id, Boolean enable) {
		JPAQuery query = createJPAQuery();
		query.whereNullableContains("user_id", user_id);
		query.whereNullableContains("user_name", user_name);
		query.whereNullableContains("roles_ids", role_id);
		query.whereNullableEqual("enable", enable);
		query.orderByDesc("user_id");
		return query.getPage(page_no, page_size);
	}

}
