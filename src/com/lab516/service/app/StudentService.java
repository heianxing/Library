package com.lab516.service.app;

import org.springframework.stereotype.Service;

import com.lab516.base.BaseService;
import com.lab516.base.JPAQuery;
import com.lab516.base.Page;
import com.lab516.entity.app.Student;

@Service
public class StudentService extends BaseService<Student> {

	public Page findPage(int page_no, int page_size, String stu_id, String stu_name) {
		JPAQuery query = createJPAQuery();
		query.whereNullableContains("stu_id", stu_id);
		query.whereNullableContains("stu_name", stu_name);
		query.orderByAsc("stu_id");
		return query.getPage(page_no, page_size);
	}

}
