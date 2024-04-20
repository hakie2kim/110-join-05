package com.portfolio.www.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.www.dao.MemberDao;
import com.portfolio.www.domain.SignUpForm;
import com.portfolio.www.domain.LoginForm;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	
	public int signUp(SignUpForm form) {
		if (!verifyUsernameLength(form.getUsername())) {
			return -1;
		}
		
		if (!verifyUsernameDuplicates(form.getUsername())) {
			return -2;
		}
		
		form.setPassword(encPassword(form.getPassword()));
		return memberDao.signUp(form);
	}

	// 회원ID 6자 이하 가입 불가
	private boolean verifyUsernameLength(String username) {
		if (username.length() <= 6) {
			return false;
		}
		
		return true;
	}
	
	// ID 중복 확인해보기
	private boolean verifyUsernameDuplicates(String username) {
		if (memberDao.cntMemberByUsername(username) > 0) {
			return false;
		}
		
		return true;
	}
	
	public boolean login(LoginForm form) {
		String encPasswordOfMember = memberDao.findEncPasswdByUsername(form);
		BCrypt.Result result = BCrypt.verifyer().verify(form.getPassword().toCharArray(), encPasswordOfMember);
		return result.verified;
	}
	
	private String encPassword(String password) {
		String encPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
		System.out.println("encPasswd >>>>>>>>> " + encPassword);

		BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), encPassword);
		System.out.println("result.verified >>>>>>>>> " + result.verified);
		
		return encPassword;
	}
}
