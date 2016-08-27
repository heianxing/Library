package com.lab516.web.sys.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lab516.base.BaseController;
import com.lab516.entity.sys.Role;
import com.lab516.entity.sys.User;
import com.lab516.service.sys.RoleService;
import com.lab516.service.sys.UserService;
import com.lab516.support.map.EhModelMap;
import com.lab516.support.request.EhWebRequest;
import com.lab516.support.validate.annotation.Validate;

@Controller
@RequestMapping("/sys/user")
@Validate(User.class)
public class UserEdit extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	public void loadData(EhModelMap map) {
		List<Role> roles = roleService.findAll(); // 角色
		map.putModels(roles);
	}

	@RequestMapping(value = EDIT, method = RequestMethod.GET)
	public String onPageLoad(EhModelMap map, String id) {
		User user = userService.findById(id);
		map.putModel("user_", user);

		loadData(map);
		return FM_FTL;
	}

	@RequestMapping(value = EDIT, method = RequestMethod.POST)
	public String onSubmit(EhWebRequest req, EhModelMap map, User user) {
		if (req.hasBindErrors()) {
			loadData(map);
			return FM_FTL;
		}

		userService.update(user);

		return GOTO_LIST;
	}

}
