package com.lab516.web.sys.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lab516.base.BaseController;
import com.lab516.entity.sys.Menu;
import com.lab516.entity.sys.Role;
import com.lab516.service.sys.MenuService;
import com.lab516.service.sys.RoleService;
import com.lab516.support.map.EhModelMap;
import com.lab516.support.request.EhWebRequest;
import com.lab516.support.validate.annotation.Validate;

@Controller
@RequestMapping("/sys/menu")
@Validate(Menu.class)
public class MenuAdd extends BaseController {

	@Autowired
	private MenuService menuService;

	@Autowired
	private RoleService roleService;

	public void loadData(EhModelMap map) {
		List<Menu> menus = menuService.findParentCandidates(); // 可选的上级菜单
		List<Role> roles = roleService.findAll(); // 所有角色
		map.putModels(menus, roles);
	}

	@RequestMapping(value = ADD, method = RequestMethod.GET)
	public String onPageLoad(EhModelMap map) {
		Menu menu = new Menu();
		menu.setEnable(true);
		map.putModels(menu);

		loadData(map);
		return FM_FTL;
	}

	@RequestMapping(value = ADD, method = RequestMethod.POST)
	public String onSubmit(EhWebRequest req, EhModelMap map, Menu menu) {
		if (req.hasBindErrors()) {
			loadData(map);
			return FM_FTL;
		}

		menuService.insert(menu);

		return GOTO_LIST;
	}

}
