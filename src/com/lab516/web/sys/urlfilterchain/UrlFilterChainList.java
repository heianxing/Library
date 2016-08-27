package com.lab516.web.sys.urlfilterchain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lab516.base.BaseController;
import com.lab516.base.Page;
import com.lab516.service.sys.UrlFilterChainService;
import com.lab516.support.map.EhModelMap;

@Controller
@RequestMapping("/sys/urlfilterchain")
public class UrlFilterChainList extends BaseController {

	@Autowired
	private UrlFilterChainService ufcService;

	@RequestMapping(LIST)
	public String loadPage(EhModelMap map, @RequestParam(defaultValue = "1") int page_no, String filt_name,
			String filt_url, String filt_chain) {

		Page page = ufcService.findPage(page_no, PAGE_SIZE, filt_name, filt_url, filt_chain);
		map.putModels(page);

		return DFT_FTL;
	}

	@ResponseBody
	@RequestMapping(DELETE)
	public boolean delete(String id) {
		ufcService.delete(id);
		return true;
	}

}
