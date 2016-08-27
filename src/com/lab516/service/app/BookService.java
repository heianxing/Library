package com.lab516.service.app;

import java.io.IOException;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lab516.base.BaseService;
import com.lab516.base.JPAQuery;
import com.lab516.base.Page;
import com.lab516.base.PageQuery;
import com.lab516.entity.app.Book;

@Service
public class BookService extends BaseService<Book> {

	@Autowired
	private UploadService uploadService;

	@Transactional
	public void insert(Book book, MultipartFile book_img) throws IOException {
		String fileName = uploadService.save(book_img);
		book.setBook_img(fileName);
		book.setIs_borrowed(false);
		insert(book);
	}

	@Transactional
	public void update(Book book, MultipartFile book_img) throws IOException {
		Book origin = findById(book.getBook_id());
		book.setBorrowInfoList(origin.getBorrowInfoList());

		if (!book_img.isEmpty()) {
			String fileName = uploadService.save(book_img);
			book.setBook_img(fileName);
		}

		update(book);
	}

	@Transactional
	public void updateIsBorrowed(String book_id, boolean is_borrowed) {
		String hql = "update Book set is_borrowed = :is_borrowed where book_id = :book_id";
		Query query = em.createQuery(hql);
		query.setParameter("book_id", book_id);
		query.setParameter("is_borrowed", is_borrowed);
		query.executeUpdate();
	}

	public Page findPage(int page_no, int page_size, String book_id, String book_name, String book_type) {
		List<Book> bookList = findList(page_no, page_size, book_id, book_name, book_type);
		long bookCount = findCount(book_id, book_name, book_type);
		return new Page(bookList, bookCount, page_size, page_no);
	}

	private List<Book> findList(int page_no, int page_size, String book_id, String book_name, String book_type) {
		PageQuery query = createPageQuery();
		query.append("from Book t01 left join fetch t01.borrowInfoList t02 where 1=1 ");
		query.whereNullableContains("and t01.book_id   like :book_id", book_id);
		query.whereNullableContains("and t01.book_name like :book_name", book_name);
		query.whereNullableContains("and t01.book_type like :book_type", book_type);
		query.append("order by t01.book_id, t02.borrow_time desc");
		return query.getListByHql(page_no, page_size);
	}

	private long findCount(String book_id, String book_name, String book_type) {
		JPAQuery query = createJPAQuery();
		query.whereNullableContains("book_id", book_id);
		query.whereNullableContains("book_name", book_name);
		query.whereNullableContains("book_type", book_type);
		return query.getRecordCount();
	}

}
