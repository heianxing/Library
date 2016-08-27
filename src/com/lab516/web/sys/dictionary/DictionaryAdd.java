package com.lab516.web.sys.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lab516.base.BaseController;
import com.lab516.entity.sys.Dictionary;
import com.lab516.service.sys.DictionaryService;
import com.lab516.support.request.EhWebRequest;
import com.lab516.support.validate.annotation.Validate;

@Controller
@RequestMapping("/sys/dictionary")
@Validate(Dictionary.class)
public class DictionaryAdd extends BaseController {

	@Autowired
	private DictionaryService dictService;

	@RequestMapping(value = ADD, method = RequestMethod.GET)
	public String onPageLoad() {
		return FM_FTL;
	}

	@RequestMapping(value = ADD, method = RequestMethod.POST)
	public String onSubmit(EhWebRequest req, Dictionary dict) {
		if (req.hasBindErrors()) {
			return FM_FTL;
		}

		dictService.insert(dict);

		return GOTO_LIST;
	}

}
