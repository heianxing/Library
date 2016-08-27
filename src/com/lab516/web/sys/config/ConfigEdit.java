package com.lab516.web.sys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lab516.base.BaseController;
import com.lab516.entity.sys.Config;
import com.lab516.service.sys.ConfigService;
import com.lab516.support.map.EhModelMap;
import com.lab516.support.request.EhWebRequest;
import com.lab516.support.validate.annotation.Validate;

@Controller
@RequestMapping("/sys/config")
@Validate(Config.class)
public class ConfigEdit extends BaseController {

	@Autowired
	private ConfigService configService;

	@RequestMapping(value = EDIT, method = RequestMethod.GET)
	public String onPageLoad(EhModelMap map, String id) {
		Config config = configService.findById(id);
		map.putModels(config);

		return FM_FTL;
	}

	@RequestMapping(value = EDIT, method = RequestMethod.POST)
	public String onSubmit(EhWebRequest req, Config config) {
		if (req.hasBindErrors()) {
			return FM_FTL;
		}

		configService.update(config);

		return GOTO_LIST;
	}

}
