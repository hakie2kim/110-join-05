package com.portfolio.www.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	@RequestMapping("/main-page.do")
	public String mainPage() {
		return "main";
	}
}
