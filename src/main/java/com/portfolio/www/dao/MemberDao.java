package com.portfolio.www.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.portfolio.www.domain.SignUpForm;
import com.portfolio.www.domain.LoginForm;

public class MemberDao extends JdbcTemplate {
	@Autowired
	DataSource dataSouce;
	
	public int signUp(SignUpForm form) {
		String sql = "INSERT INTO forum.`member` "
				+ "(member_id, passwd, email, pwd_chng_dtm, join_dtm) "
				+ "VALUES('" + form.getUsername()
				+ "', '" + form.getPassword()
				+ "', '" + form.getEmail()
				+ "', DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'), DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'));";
		return update(sql);
	}
	
	public int cntMemberByUsername(String username) {
		String sql = String.format("SELECT COUNT(*) FROM forum.`member` WHERE member_id = '%s';", username);
		return queryForObject(sql, Integer.class);
	}
	
	public String findEncPasswdByUsername(LoginForm form) {
		String sql = String.format("SELECT passwd FROM forum.`member` WHERE member_id = '%s';", form.getUsername());
		return queryForObject(sql, String.class);
	}
}
