package com.portfolio.www.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.portfolio.www.domain.SignUpForm;
import com.portfolio.www.dto.MemberDto;
import com.portfolio.www.domain.LoginForm;

public class MemberDao extends JdbcTemplate {
	@Autowired
	DataSource dataSouce;
	
	public int signUp(SignUpForm form) {
		/*
		 * String sql = "INSERT INTO forum.`member` " +
		 * "(member_id, passwd, email, pwd_chng_dtm, join_dtm) " + "VALUES('" +
		 * form.getUsername() + "', '" + form.getPassword() + "', '" + form.getEmail() +
		 * "', DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'), DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'));"
		 * ;
		 */
		String sql = "INSERT INTO forum.`member` (member_id, passwd, email, pwd_chng_dtm, join_dtm) "
				+ "VALUES(?, ?, ?, DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'), DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'));";
		Object[] args = {form.getUsername(), form.getPassword(), form.getEmail()};
		return update(sql, args);
	}
	
	public int cntMemberByUsername(String username) {
//		String sql = String.format("SELECT COUNT(*) FROM forum.`member` WHERE member_id = '%s';", username);
		String sql = "SELECT COUNT(*) FROM forum.`member` WHERE member_id = ?;";
		Object[] args = {username};
		return queryForObject(sql, Integer.class, args);
	}
	
	public MemberDto findMemberByUsername(String username) {
		String sql = "SELECT * FROM forum.`member` WHERE member_id = ?;";
		Object[] args = {username};
		return queryForObject(sql, new MemberRowMapper(), args);
	}
	
	private RowMapper<MemberDto> memberRowMapper() {
		return (rs, rowNum) -> {
			MemberDto memberDto = new MemberDto();
			memberDto.setMemberSeq(rs.getInt("member_seq"));
			memberDto.setMemberId(rs.getString("member_id"));
			memberDto.setPasswd(rs.getString("passwd"));
			memberDto.setMemberNm(rs.getString("member_nm"));
			memberDto.setEmail(rs.getString("email"));
			memberDto.setAuthYn(rs.getString("auth_yn"));
			memberDto.setPwdChngDtm(rs.getString("pwd_chng_dtm"));
			memberDto.setJoinDtm(rs.getString("join_dtm"));
			return memberDto;
		};
	}
	
	class MemberRowMapper implements RowMapper<MemberDto> {
		@Override
		public MemberDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			MemberDto memberDto = new MemberDto();
			memberDto.setMemberSeq(rs.getInt("member_seq"));
			memberDto.setMemberId(rs.getString("member_id"));
			memberDto.setPasswd(rs.getString("passwd"));
			memberDto.setMemberNm(rs.getString("member_nm"));
			memberDto.setEmail(rs.getString("email"));
			memberDto.setAuthYn(rs.getString("auth_yn"));
			memberDto.setPwdChngDtm(rs.getString("pwd_chng_dtm"));
			memberDto.setJoinDtm(rs.getString("join_dtm"));
			return memberDto;
		}
	}
	
	/*
	 * public String findEncPasswdByUsername(LoginForm form) { String sql =
	 * String.format("SELECT passwd FROM forum.`member` WHERE member_id = '%s';",
	 * form.getUsername()); return queryForObject(sql, String.class); }
	 */
	
	public int findMemberSeqByUsername(String username) {
//		String sql = String.format("SELECT member_seq FROM forum.`member` WHERE member_id = '%s';", username);
		String sql = "SELECT member_seq FROM forum.`member` WHERE member_id = ?;";
		Object[] args = {username};
		return queryForObject(sql, Integer.class, args);
	}
}
