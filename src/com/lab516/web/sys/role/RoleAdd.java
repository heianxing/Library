package com.lab516.web.sys.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lab516.base.BaseController;
import com.lab516.entity.sys.Role;
import com.lab516.service.sys.RoleService;
import com.lab516.support.request.EhWebRequest;
import com.lab516.support.validate.annotation.Validate;

@Controller
@RequestMapping("/sys/role")
@Validate(Role.class)
public class RoleAdd extends BaseController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = ADD, method = RequestMethod.GET)
	public String onPageLoad() {
		return FM_FTL;
	}

	@RequestMapping(value = ADD, method = RequestMethod.POST)
	public String onSubmit(EhWebRequest req, Role role) {
		if (req.hasBindErrors()) {
			return FM_FTL;
		}

		Role r = roleService.findById(role.getRole_id());

		if (r != null) {
			req.addBindError("该角色Id已经被使用");
			return FM_FTL;
		}

		roleService.insert(role);

		return GOTO_LIST;
	}

}
