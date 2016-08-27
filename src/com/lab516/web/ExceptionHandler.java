package com.lab516.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionHandler {

	@RequestMapping(value = "exceptionhandler")
	public String loadPage(Exception ex) {
		ex.printStackTrace(); // TODO log
		return "ExceptionHandler.ftl";
	}

}
