package com.lab516.support.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab516.base.Consts;
import com.lab516.entity.sys.User;
import com.lab516.service.sys.UserService;

@Service
public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	// 登陆
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		try {
			String user_id = (String) token.getPrincipal();
			String password = new String((char[]) token.getCredentials());

			User user = userService.login(user_id, password);

			return new SimpleAccount(user, password, getName());
		} catch (Exception ex) {
			throw new AuthenticationException(ex.getMessage());
		}
	}

	// 授权 通过用户获取Permissions和 Roles
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		User user = (User) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		String[] roles_id = user.getRoles_ids().split(Consts.DBC_SPLIT);
		for (String roid : roles_id) {
			// 添加角色
			info.addRole(roid);

			// 添加权限...
		}

		return info;
	}

}
