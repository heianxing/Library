package com.lab516.web.app.borrowbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lab516.base.BaseController;
import com.lab516.base.Page;
import com.lab516.service.app.BorrowService;
import com.lab516.support.map.EhModelMap;
import com.lab516.support.request.EhWebRequest;

@Controller
@RequestMapping("/app/borrowbook")
public class BorrowInfokList extends BaseController {

	@Autowired
	private BorrowService borrowService;

	@RequestMapping(LIST)
	public String onPageLoad(EhWebRequest req, EhModelMap map, @RequestParam(defaultValue = "1") int page_no,
			String book_id, String book_name, String stu_id, String stu_name) {

		Page page = borrowService.findPage(page_no, PAGE_SIZE, book_id, book_name, stu_id, stu_name);
		map.putModels(page);

		return DFT_FTL;
	}

}
