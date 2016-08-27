package com.lab516.web.sys.urlfilterchain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lab516.base.BaseController;
import com.lab516.entity.sys.UrlFilterChain;
import com.lab516.service.sys.UrlFilterChainService;
import com.lab516.support.map.EhModelMap;
import com.lab516.support.request.EhWebRequest;
import com.lab516.support.validate.annotation.Validate;

@Controller
@RequestMapping("/sys/urlfilterchain")
@Validate(UrlFilterChain.class)
public class UrlFilterChainEdit extends BaseController {

	@Autowired
	private UrlFilterChainService ufcService;

	@RequestMapping(value = EDIT, method = RequestMethod.GET)
	public String onPageLoad(EhModelMap map, String id) {
		UrlFilterChain ufc = ufcService.findById(id);
		map.putModels(ufc);

		return FM_FTL;
	}

	@RequestMapping(value = EDIT, method = RequestMethod.POST)
	public String onSubmit(EhWebRequest req, UrlFilterChain ufc) {
		if (req.hasBindErrors()) {
			return FM_FTL;
		}

		ufcService.update(ufc);

		return GOTO_LIST;
	}

}
