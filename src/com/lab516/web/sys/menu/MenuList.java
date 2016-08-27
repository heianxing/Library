package com.lab516.web.sys.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lab516.base.BaseController;
import com.lab516.base.Page;
import com.lab516.entity.sys.Role;
import com.lab516.service.sys.MenuService;
import com.lab516.service.sys.RoleService;
import com.lab516.support.map.EhModelMap;

@Controller
@RequestMapping("/sys/menu")
public class MenuList extends BaseController {

	@Autowired
	private MenuService menuService;

	@Autowired
	private RoleService roleService;

	@RequestMapping(LIST)
	public String loadPage(EhModelMap map, @RequestParam(defaultValue = "1") int page_no, String menu_name,
			String menu_url) {

		menuService.deleteInvalid();

		List<Role> roles = roleService.findAll();
		Page page = menuService.findPage(page_no, PAGE_SIZE, menu_name, menu_url);
		map.putModels(roles, page);

		return DFT_FTL;
	}

	@ResponseBody
	@RequestMapping("changerole")
	public boolean changeRole(String menu_id, String role_id, boolean isGrantRole) {
		if (isGrantRole) {
			menuService.addMenusRole(menu_id, role_id);
		} else {
			menuService.removeMenusRole(menu_id, role_id);
		}
		return true;
	}

	@ResponseBody
	@RequestMapping(DELETE)
	public boolean delete(String id) {
		menuService.delete(id);
		return true;
	}

}
