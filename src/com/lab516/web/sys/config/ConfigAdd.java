package com.lab516.web.sys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lab516.base.BaseController;
import com.lab516.entity.sys.Config;
import com.lab516.service.sys.ConfigService;
import com.lab516.support.request.EhWebRequest;
import com.lab516.support.validate.annotation.Validate;

@Controller
@RequestMapping("/sys/config")
@Validate(Config.class)
public class ConfigAdd extends BaseController {

	@Autowired
	private ConfigService configService;

	@RequestMapping(value = ADD, method = RequestMethod.GET)
	public String onPageLoad() {
		return FM_FTL;
	}

	@RequestMapping(value = ADD, method = RequestMethod.POST)
	public String onSubmit(EhWebRequest req, Config config) {
		if (req.hasBindErrors()) {
			return FM_FTL;
		}

		Config cfg = configService.findById(config.getCfg_id());
		
		if (cfg != null) {
			req.addBindError("该配置ID已经被使用");
			return FM_FTL;
		}

		configService.insert(config);

		return GOTO_LIST;
	}

}
