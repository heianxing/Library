package com.lab516.web.sys.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lab516.base.BaseController;
import com.lab516.base.Page;
import com.lab516.service.sys.RoleService;
import com.lab516.support.map.EhModelMap;

@Controller
@RequestMapping("/sys/role")
public class RoleList extends BaseController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(LIST)
	public String loadPage(EhModelMap map, @RequestParam(defaultValue = "1") int page_no, String role_id,
			String role_name) {

		Page page = roleService.findPage(page_no, PAGE_SIZE, role_id, role_name);
		map.putModels(page);

		return DFT_FTL;
	}

	@ResponseBody
	@RequestMapping(DELETE)
	public boolean delete(String id) {
		roleService.delete(id);
		return true;
	}

}
