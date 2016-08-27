package com.lab516.web.app.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lab516.base.BaseController;
import com.lab516.entity.sys.Menu;
import com.lab516.service.sys.MenuService;

@Controller
@RequestMapping("/app/demo")
public class Form extends BaseController {

	private static final String FTL_URL = "/app/demo/Form.ftl";

	private static final String NONE = "-1";

	@Autowired
	private MenuService menuService;

	@RequestMapping(value = "form")
	public String onPageLoad(HttpServletRequest request, ModelMap map, Date _dateField) {
		System.out.println("_dateField:" + _dateField);
		System.out.println("paletteFood:" + request.getParameter("paletteFood"));
		System.out.println("checkBoxGroupFood:" + request.getParameter("checkBoxGroupFood"));
		System.out.println("radioGroupFood:" + request.getParameter("radioGroupFood"));

		List<Object[]> rootMenus = findSubMenus(NONE);
		map.put("rootMenus", rootMenus);

		return FTL_URL;
	}

	@ResponseBody
	@RequestMapping(value = "findsubmenus")
	public List<Object[]> findSubMenus(@RequestParam(REFER_VALUE) String menu_id) {
		List<Menu> menus = menuService.findAll();

		List<Object[]> result = new ArrayList<Object[]>();

		for (Iterator<Menu> iterator = menus.iterator(); iterator.hasNext();) {
			Menu menu = iterator.next();

			if (menu.getParent_id().equals(menu_id)) {
				result.add(new Object[] { menu.getMenu_id(), menu.getMenu_name() });
			}
		}

		return result;
	}

}
