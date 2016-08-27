package com.lab516.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Unauthorized {

	@RequestMapping("unauthorized")
	public String loadPage() {
		return "Unauthorized.ftl";
	}

}
