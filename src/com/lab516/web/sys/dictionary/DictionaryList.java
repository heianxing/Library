package com.lab516.web.sys.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lab516.base.BaseController;
import com.lab516.base.Page;
import com.lab516.service.sys.DictionaryService;
import com.lab516.support.map.EhModelMap;
import com.lab516.support.request.EhWebRequest;

@Controller
@RequestMapping("/sys/dictionary")
public class DictionaryList extends BaseController {

	@Autowired
	private DictionaryService dictService;

	@RequestMapping(LIST)
	public String loadPage(EhWebRequest req, EhModelMap map, @RequestParam(defaultValue = "1") int page_no,
			String dict_name, String dict_type) {

		Page page = dictService.findPage(page_no, PAGE_SIZE, dict_name, dict_type);
		map.putModels(page);

		return DFT_FTL;
	}

	@ResponseBody
	@RequestMapping(DELETE)
	public boolean delete(String id) {
		dictService.delete(id);
		return true;
	}

}
