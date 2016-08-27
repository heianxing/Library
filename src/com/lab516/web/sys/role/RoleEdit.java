package com.lab516.web.sys.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lab516.base.BaseController;
import com.lab516.entity.sys.Role;
import com.lab516.service.sys.RoleService;
import com.lab516.support.map.EhModelMap;
import com.lab516.support.request.EhWebRequest;
import com.lab516.support.validate.annotation.Validate;

@Controller
@RequestMapping("/sys/role")
@Validate(Role.class)
public class RoleEdit extends BaseController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = EDIT, method = RequestMethod.GET)
	public String onPageLoad(EhModelMap map, String id) {
		Role role = roleService.findById(id);
		map.putModels(role);

		return FM_FTL;
	}

	@RequestMapping(value = EDIT, method = RequestMethod.POST)
	public String onSubmit(EhWebRequest req, EhModelMap map, Role role) {
		if (req.hasBindErrors()) {
			return FM_FTL;
		}

		roleService.update(role);

		return GOTO_LIST;
	}

}
