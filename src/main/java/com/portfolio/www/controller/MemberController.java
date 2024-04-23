package com.portfolio.www.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.portfolio.www.domain.SignUpForm;
import com.portfolio.www.domain.LoginForm;
import com.portfolio.www.service.MemberService;

@Controller
public class MemberController {
	@Autowired
	MemberService memberService;
	
	@RequestMapping("/join-page.do")
	public String joinPage() {
		return "login";
	}
	
	@RequestMapping("/sign-up.do")
	public String signUp(@ModelAttribute SignUpForm form, Model model) {
		int result = memberService.signUp(form);
		
		String msg = "회원가입에 성공했습니다.";
		switch (result) {
			case -1:
				msg = "회원 ID는 7자 이상이어야 합니다.";
				break;
			case -2:
				msg = "중복 등록된 ID입니다.";
				break;
			case -3:
				msg = "인증 메일 발송에 실패했습니다. 관리자에게 문의하세요.";
		}
		model.addAttribute("msg", msg);
		
		return "login";
	}
	
	@RequestMapping("/login.do")
	public String login(@ModelAttribute LoginForm form, Model model) {	
		String msg = "로그인에 실패했습니다.";
		if (memberService.login(form)) {
			msg = "로그인에 성공했습니다.";
		}
		model.addAttribute("msg", msg);
		
		return "login";
	}
	
	@RequestMapping("/emailAuth.do")
	public String emailAuth(@RequestParam("uri") String uri, Model model) {
		String msg = "회원 인증에 실패했습니다.";
		if (memberService.emailAuth(uri)) {
			msg = "회원 인증에 성공했습니다.";
		}
		model.addAttribute("msg", msg);
		
		return "login";
	}
}
