package com.lab516.web.app.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lab516.base.BaseController;
import com.lab516.service.app.BorrowService;
import com.lab516.support.map.EhModelMap;

@Controller
@RequestMapping("/app/statistics/chart")
public class Chart extends BaseController {

	@Autowired
	private BorrowService borrowService;

	@RequestMapping("showbookborrowtimes")
	public String showBookBorrowTimes(EhModelMap map) {
		Object data = borrowService.findBookBorrowTimes();
		map.putModel("data", data);

		return "/app/statistics/BookBorrowTimes.ftl";
	}
	
	// TODO 这页面在ubuntu会抛出404错误
	@RequestMapping("showbookborrowtimespastmonth")
	public String showBookBorrowTimesPastMonth(EhModelMap map) {
		Object data = borrowService.findBookBorrowTimesPastMonth();
		map.putModel("data", data);

		return "/app/statistics/Bookborrowtimespastmonth.ftl";
	}

}
