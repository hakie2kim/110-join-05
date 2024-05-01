package com.portfolio.www.message;

public enum Message {
	SUCCESS("0000", "성공"),
	
	SIGN_UP_SUCCESS("8000", "회원가입에 성공했습니다."),
	ID_LENGTH_LIMIT("8001", "회원 ID는 7자 이상이어야 합니다."),
	ID_EXISTS("8002", "존재하는 아이디입니다."),
	AUTH_EMAIL_SEND_FAIL("8003", "인증 메일 발송에 실패했습니다. 관리자에게 문의하세요."),
	
	USER_NOT_FOUND("9000", "존재하지 않는 아이디입니다."),
	PASSWORD_NOT_MATCH("9001", "패스워드가 일치하지 않습니다."),
	
	MEMBER_AUTH_SUCCESS("7000", "회원 인증에 성공했습니다."),
	MEMBER_AUTH_FAIL("7001", "회원 인증에 실패했습니다.");
	
	private String code;
	private String description;

	Message(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}	
}
