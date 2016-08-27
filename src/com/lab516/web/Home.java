package com.lab516.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lab516.base.BaseController;
import com.lab516.base.Consts;
import com.lab516.entity.sys.Menu;
import com.lab516.service.sys.MenuService;
import com.lab516.service.sys.UserService;
import com.lab516.support.map.EhModelMap;
import com.lab516.support.request.EhWebRequest;

@Controller
public class Home extends BaseController {

	@Autowired
	private MenuService menuService;

	@Autowired
	private UserService userService;

	@RequestMapping("home")
	public String loadPage(EhWebRequest req, EhModelMap map, String user_id) {
		// 根据当前用户的角色获取菜单
		String[] roles_id = UserService.currentUser().getRoles_ids().split(Consts.DBC_SPLIT);
		List<Menu> menus = menuService.findTreeByRolesId(roles_id);

		map.putModels(menus);

		return DFT_FTL;
	}

	@ResponseBody
	@RequestMapping("changepwd")
	public boolean changePwd(String oldPwd, String newPwd) {
		try {
			userService.changePassword(UserService.currentUserId(), oldPwd, newPwd);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
