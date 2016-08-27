package com.lab516.web.app.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lab516.base.BaseController;
import com.lab516.base.Page;
import com.lab516.service.app.StudentService;
import com.lab516.support.map.EhModelMap;

@Controller
@RequestMapping("/app/student")
public class StudentList extends BaseController {

	@Autowired
	private StudentService stuService;

	@RequestMapping(LIST)
	public String loadPage(EhModelMap map, @RequestParam(defaultValue = "1") int page_no, String stu_id,
			String stu_name) {

		Page page = stuService.findPage(page_no, PAGE_SIZE, stu_id, stu_name);
		map.putModels(page);

		return DFT_FTL;
	}

	@ResponseBody
	@RequestMapping(DELETE)
	public boolean delete(String id) {
		stuService.delete(id);
		return true;
	}

}
