package com.lab516.web.sys.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lab516.base.BaseController;
import com.lab516.entity.sys.Role;
import com.lab516.entity.sys.User;
import com.lab516.service.sys.RoleService;
import com.lab516.service.sys.UserService;
import com.lab516.support.map.EhModelMap;

@Controller
@RequestMapping("/sys/user")
public class UserView extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@RequestMapping(VIEW)
	public String onPageLoad(EhModelMap map, String id) {
		List<Role> roles = roleService.findAll(); // 角色
		User user = userService.findById(id); // 用户
		map.putModels(roles, user);

		return DFT_FTL;
	}

}
