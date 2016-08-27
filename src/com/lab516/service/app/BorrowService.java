package com.lab516.service.app;

import java.util.Date;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lab516.base.BaseService;
import com.lab516.base.Page;
import com.lab516.base.PageQuery;
import com.lab516.entity.app.BorrowInfo;

@Service
public class BorrowService extends BaseService<BorrowInfo> {

	@Autowired
	private BookService bookService;

	@Transactional
	public void borrowBook(String book_id, String stu_id) {
		BorrowInfo borrowInfo = new BorrowInfo();
		borrowInfo.setBrw_id(getUUID());
		borrowInfo.setBorrow_time(new Date());
		borrowInfo.setIs_returned(false);
		borrowInfo.setStu_id(stu_id);
		borrowInfo.setBook_id(book_id);
		update(borrowInfo);

		bookService.updateIsBorrowed(book_id, true);
	}

	@Transactional
	public void returnBook(String book_id) {
		String hql = "update BorrowInfo set is_returned = true, return_time = :return_time where book_id = :book_id";
		Query query = em.createQuery(hql);
		query.setParameter("book_id", book_id);
		query.setParameter("return_time", new Date());
		query.executeUpdate();

		bookService.updateIsBorrowed(book_id, false);
	}

	public Page findPage(int page_no, int page_size, String book_id, String book_name, String stu_id, String stu_name) {
		PageQuery query = createPageQuery();
		query.append("select t01.book_id, t02.book_name, t01.stu_id, t03.stu_name, t01.is_returned,"
				+ "          t01.borrow_time, t01.return_time   								   "
				+ "   from   a_borrowinfo t01 left join a_book t02    on t01.book_id = t02.book_id "
				+ "                           left join a_student t03 on t01.stu_id  = t03.stu_id  "
				+ "   where  1 = 1  ");
		query.whereNullableContains("and t01.book_id   like :book_id", book_id);
		query.whereNullableContains("and t02.book_name like :book_name", book_name);
		query.whereNullableContains("and t01.stu_id    like :stu_id", stu_id);
		query.whereNullableContains("and t03.stu_name  like :stu_name", stu_name);
		query.append("order by t01.borrow_time desc");
		return query.getPageBySql(page_no, page_size);
	}

	// 图书借阅次数统计
	public Object findBookBorrowTimes() {
		String sql = "select t01.book_name, count(*)                            "
				+ "   from   a_book t01 left join a_borrowinfo t02 on t01.book_id = t02.book_id"
				+ "   group by t01.book_id                                      "
				+ "   order by count(*) desc                                    ";
		Query query = em.createNativeQuery(sql);
		return query.getResultList();
	}

	// 过去15天借书次数统计
	public Object findBookBorrowTimesPastMonth() {
		// 获取过去半个月日期的sql片段
		StringBuilder daySql = new StringBuilder("select date(now()) as day ");

		for (int i = -1; i > -15; i--) {
			daySql.append("union all select date_add(date(now()),interval " + i + " day) as day ");
		}

		// 过去一个月借书次数sql
		String borrowSql = "select day, count(brw_id) from 								"
				+ "		    ( select day from ( " + daySql + " ) t01 ) t03 				"
				+ "				left join a_borrowinfo t02 on t03.day = t02.borrow_time "
				+ "			group by day												"
				+ "         order by day desc											";
		Query query = em.createNativeQuery(borrowSql);
		return query.getResultList();
	}

}
