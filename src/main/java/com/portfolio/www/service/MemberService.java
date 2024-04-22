package com.portfolio.www.service;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.www.dao.MemberAuthDao;
import com.portfolio.www.dao.MemberDao;
import com.portfolio.www.domain.SignUpForm;
import com.portfolio.www.dto.EmailDto;
import com.portfolio.www.dto.MemberAuthDto;
import com.portfolio.www.util.EmailUtil;
import com.portfolio.www.domain.LoginForm;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private MemberAuthDao memberAuthDao;
	
	@Autowired
	private EmailUtil emailUtil;	
	
	public int signUp(SignUpForm form) {
		if (!verifyUsernameLength(form.getUsername())) {
			return -1;
		}
		
		if (!verifyUsernameDuplicates(form.getUsername())) {
			return -2;
		}
		
		form.setPassword(encPassword(form.getPassword()));
		int result = memberDao.signUp(form);
		
		if (result == 1) {
			// 1. 인증에 필요한 정보 dto 만든 후 MemberAuth 테이블에 삽입
			MemberAuthDto memAuthDto = makeMemberAuthDto(form);
			memberAuthDao.addMemberAuthInfo(memAuthDto);
			
			// 2. 인증 메일 전송에 필요한 dto 만든 후 인증 메일 보내기
			EmailDto emailDto = makeEmailDto(form, memAuthDto);
			String sendEmailResult = emailUtil.sendEmail(emailDto);
			
			// 인증 메일 발송에 실패한 경우
			if (sendEmailResult.equals("Error"))
				return -3;
		}
		
		return 0;
	}

	private EmailDto makeEmailDto(SignUpForm form, MemberAuthDto memAuthDto) {
		EmailDto emailDto = new EmailDto();
		emailDto.setFrom("portfolio98@naver.com");
		emailDto.setReceiver(form.getEmail());
		emailDto.setSubject("회원 가입 인증을 위한 메일 안내입니다.");
		
		// host + context root + uri
		String html = String.format("<a href='http://localhost:8080/11005/emailAuth.do?uri=%s'>회원 가입 인증 하기</>",
					memAuthDto.getAuthUri()
				);
		emailDto.setText(html);
		
		return emailDto;
	}

	private MemberAuthDto makeMemberAuthDto(SignUpForm form) {
		// 1. 인증에 필요한 정보 삽입
		MemberAuthDto dto = new MemberAuthDto();
		
		// 1-1. 회원가입할 대상의 member_seq 찾기
		int memberSeq = memberDao.findMemberSeqByUsername(form.getUsername());
		dto.setMemberSeq(memberSeq);
		
		// 1-2. 인증 링크 만들기
		dto.setAuthUri(UUID.randomUUID().toString().replace("-", ""));
		
		// 1-3. 인증 링크 만료일자 설정
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 30); // 30분만 유효
		dto.setExpireDtm(cal.getTimeInMillis());
		return dto;
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
		
//		if (result.verified)
		
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
