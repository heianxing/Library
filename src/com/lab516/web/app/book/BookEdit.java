package com.lab516.web.app.book;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.lab516.base.BaseController;
import com.lab516.base.Consts;
import com.lab516.entity.app.Book;
import com.lab516.entity.sys.Dictionary;
import com.lab516.service.app.BookService;
import com.lab516.service.sys.DictionaryService;
import com.lab516.support.map.EhModelMap;
import com.lab516.support.request.EhWebRequest;
import com.lab516.support.validate.annotation.Validate;

@Controller
@RequestMapping("/app/book")
@Validate(Book.class)
public class BookEdit extends BaseController {

	@Autowired
	private BookService bookService;

	@Autowired
	private DictionaryService dictService;

	public void loadData(EhModelMap map) {
		List<Dictionary> bookTypes = dictService.findByType(Consts.DICT_BOOK_TYPE);
		map.putModel("bookTypes", bookTypes);
	}

	@RequestMapping(value = EDIT, method = RequestMethod.GET)
	public String onPageLoad(EhModelMap map, String id) {
		Book book = bookService.findById(id);
		map.putModels(book);

		loadData(map);

		return DFT_FTL;
	}

	@RequestMapping(value = EDIT, method = RequestMethod.POST)
	public String onSubmit(EhWebRequest req, EhModelMap map, Book book, MultipartFile book_img) throws IOException {
		if (req.hasBindErrors()) {
			loadData(map);
			return DFT_FTL;
		}

		bookService.update(book, book_img);

		return GOTO_LIST;
	}

}
