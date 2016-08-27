package com.lab516.web.sys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lab516.base.BaseController;
import com.lab516.base.Page;
import com.lab516.service.sys.ConfigService;
import com.lab516.support.map.EhModelMap;

@Controller
@RequestMapping("/sys/config")
public class ConfigList extends BaseController {

	@Autowired
	private ConfigService configService;

	@RequestMapping(LIST)
	public String loadPage(EhModelMap map, @RequestParam(defaultValue = "1") int page_no, String cfg_id,
			String cfg_name, String cfg_value) {

		Page page = configService.findPage(page_no, PAGE_SIZE, cfg_id, cfg_name, cfg_value);
		map.putModels(page);

		return DFT_FTL;
	}

	@ResponseBody
	@RequestMapping(DELETE)
	public boolean delete(String id) {
		configService.delete(id);
		return true;
	}

}
