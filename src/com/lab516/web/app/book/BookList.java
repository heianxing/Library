package com.lab516.web.app.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lab516.base.BaseController;
import com.lab516.base.Consts;
import com.lab516.base.Page;
import com.lab516.entity.sys.Dictionary;
import com.lab516.service.app.BookService;
import com.lab516.service.sys.DictionaryService;
import com.lab516.support.map.EhModelMap;

@Controller
@RequestMapping("/app/book")
public class BookList extends BaseController {

	@Autowired
	private BookService bookService;

	@Autowired
	private DictionaryService dictService;

	@RequestMapping(LIST)
	public String onPageLoad(EhModelMap map, @RequestParam(defaultValue = "1") int page_no, String book_id,
			String book_name, String book_type) {

		Page page = bookService.findPage(page_no, PAGE_SIZE, book_id, book_name, book_type);
		map.putModels(page);

		List<Dictionary> bookTypes = dictService.findByType(Consts.DICT_BOOK_TYPE);
		map.putModel("bookTypes", bookTypes);

		return DFT_FTL;
	}

	@ResponseBody
	@RequestMapping(DELETE)
	public boolean delete(String id) {
		bookService.delete(id);
		return true;
	}

}
