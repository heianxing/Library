package com.lab516.web.app.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lab516.base.BaseController;
import com.lab516.entity.app.Student;
import com.lab516.service.app.StudentService;
import com.lab516.support.request.EhWebRequest;
import com.lab516.support.validate.annotation.Validate;

@Controller
@RequestMapping("/app/student")
@Validate(Student.class)
public class StudentAdd extends BaseController {

	@Autowired
	private StudentService stuService;

	@RequestMapping(value = ADD, method = RequestMethod.GET)
	public String onPageLoad() {
		return FM_FTL;
	}

	@RequestMapping(value = ADD, method = RequestMethod.POST)
	public String onSubmit(EhWebRequest req, Student student) {
		if (req.hasBindErrors()) {
			return FM_FTL;
		}

		stuService.insert(student);

		return GOTO_LIST;
	}

}
