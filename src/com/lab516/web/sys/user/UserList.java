package com.lab516.web.sys.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lab516.base.BaseController;
import com.lab516.base.Consts;
import com.lab516.base.Page;
import com.lab516.entity.sys.Role;
import com.lab516.service.sys.RoleService;
import com.lab516.service.sys.UserService;
import com.lab516.support.map.EhModelMap;

@Controller
@RequestMapping("/sys/user")
public class UserList extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@RequestMapping(LIST)
	public String loadPage(EhModelMap map, @RequestParam(defaultValue = "1") int page_no, String user_id,
			String user_name, String role_id, Boolean enable) {

		List<Role> roles = roleService.findAll();
		Page page = userService.findPage(page_no, PAGE_SIZE, user_id, user_name, role_id, enable);
		map.putModels(roles, page);

		return DFT_FTL;
	}

	@ResponseBody
	@RequestMapping(DELETE)
	public boolean delete(String id) {
		userService.delete(id);
		return true;
	}

	@ResponseBody
	@RequestMapping("resetinitpwd")
	public String resetInitPwd(String user_id) {
		userService.changePassword(user_id, Consts.INIT_PWD);
		return String.format("用户【%s】的密码已被重置为【%s】", user_id, Consts.INIT_PWD);
	}

	@ResponseBody
	@RequestMapping("changeenable")
	public boolean changeEnable(String user_id, boolean enable) {
		userService.changeEnable(user_id, enable);
		return true;
	}

}
