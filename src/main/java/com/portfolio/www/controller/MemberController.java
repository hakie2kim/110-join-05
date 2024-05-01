package com.portfolio.www.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.portfolio.www.domain.SignUpForm;
import com.portfolio.www.dto.MemberDto;
import com.portfolio.www.message.Message;
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
		String result = memberService.signUp(form);
		
		String code = Message.SIGN_UP_SUCCESS.getCode();
		String msg = Message.SIGN_UP_SUCCESS.getDescription();
		if (result.equals(Message.ID_LENGTH_LIMIT.getCode())) {
			code = Message.ID_LENGTH_LIMIT.getCode();
			msg = Message.ID_LENGTH_LIMIT.getDescription();
		} else if (result.equals(Message.ID_EXISTS.getCode())) {
			code = Message.ID_EXISTS.getCode();
			msg = Message.ID_EXISTS.getDescription();
		} else if (result.equals(Message.AUTH_EMAIL_SEND_FAIL.getCode())) {
			code = Message.AUTH_EMAIL_SEND_FAIL.getCode();
			msg = Message.AUTH_EMAIL_SEND_FAIL.getDescription();
		} 
		
		model.addAttribute("code", code);
		model.addAttribute("msg", msg);
		
		return "login";
	}
	
	@RequestMapping("/login.do")
	public String login(@ModelAttribute LoginForm form, HttpServletRequest request, Model model) {	
		String code = "";
		String msg = "";
		
		try {
			MemberDto memberDto = memberService.login(form);
			
			if (!ObjectUtils.isEmpty(memberDto)) {
				// 세션 처리
				HttpSession session = request.getSession();
				session.setAttribute("memberId", memberDto.getMemberId());
				code = Message.SUCCESS.getCode();
				msg = Message.SUCCESS.getDescription();
				return "redirect:/main-page.do";
				
			} else {
				code = Message.PASSWORD_NOT_MATCH.getCode();
				msg = Message.PASSWORD_NOT_MATCH.getDescription();
			}
		} catch (EmptyResultDataAccessException e) {
			code = Message.USER_NOT_FOUND.getCode();
			msg = Message.USER_NOT_FOUND.getDescription();
		}
		
		model.addAttribute("code", code);
		model.addAttribute("msg", msg);
		
		return "login";
	}

	@RequestMapping("/emailAuth.do")
	public String emailAuth(@RequestParam("uri") String uri, Model model) {
		if (uri.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		String code = Message.MEMBER_AUTH_FAIL.getCode();
		String msg = Message.MEMBER_AUTH_FAIL.getDescription();
		if (memberService.emailAuth(uri)) {
			code = Message.MEMBER_AUTH_SUCCESS.getCode();
			msg = Message.MEMBER_AUTH_SUCCESS.getDescription();
		}
		
		model.addAttribute("code", code);
		model.addAttribute("msg", msg);
		
		return "login";
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request) {
		request.getSession(false).invalidate();
		return "redirect:/join-page.do";
	}
}
