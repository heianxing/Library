package com.lab516.web.app.borrowbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lab516.base.BaseController;
import com.lab516.entity.app.Book;
import com.lab516.entity.app.BorrowInfo;
import com.lab516.entity.app.Student;
import com.lab516.service.app.BookService;
import com.lab516.service.app.BorrowService;
import com.lab516.service.app.StudentService;
import com.lab516.support.map.EhModelMap;
import com.lab516.support.request.EhWebRequest;
import com.lab516.support.validate.annotation.Validate;

@Controller
@RequestMapping("/app/borrowbook")
@Validate(BorrowInfo.class)
public class BookBorrow extends BaseController {

	@Autowired
	private BorrowService borrowService;

	@Autowired
	private BookService bookService;

	@Autowired
	private StudentService studentService;

	@RequestMapping("borrow")
	public String onPageLoad(EhWebRequest req, EhModelMap map, String book_id, String stu_id) {
		if (isEmpty(book_id) || isEmpty(stu_id)) {
			return DFT_FTL;
		}

		Book book = bookService.findById(book_id);

		if (book == null) {
			req.addBindError("此书不存在！");
			return DFT_FTL;
		}

		if (book.isIs_borrowed()) {
			req.addBindError("此书已被借走！");
			return DFT_FTL;
		}

		Student student = studentService.findById(stu_id);

		if (student == null) {
			req.addBindError("此学生不存在！");
			return DFT_FTL;
		}

		borrowService.borrowBook(book_id, stu_id);

		map.putModel("successMsg", "图书 【" + book_id + "】借阅成功!");

		return DFT_FTL;
	}

	// 检查此图书是否可以被借出
	@ResponseBody
	@RequestMapping("checkBorrowBookById")
	public int checkBorrowBookById(String book_id) {
		Book book = bookService.findById(book_id);
		if (book == null) {
			return 1;
		}

		if (book.isIs_borrowed()) {
			return 2;
		}

		return 3;
	}

	// 检查此学生编号是否存在
	@ResponseBody
	@RequestMapping("isStudentExist")
	public boolean isStudentExist(String stu_id) {
		Student student = studentService.findById(stu_id);
		return student != null;
	}

}
