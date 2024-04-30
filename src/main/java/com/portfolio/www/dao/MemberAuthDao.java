package com.portfolio.www.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.portfolio.www.dto.MemberAuthDto;

public class MemberAuthDao extends JdbcTemplate {
	private DataSource dataSource;
	
	public int addMemberAuthInfo(MemberAuthDto dto) {
		/*
		 * String sql = String.format("INSERT INTO forum.member_auth " +
		 * "(member_seq, auth_num, auth_uri, reg_dtm, expire_dtm, auth_yn) " +
		 * "VALUES(%d, '', '%s', DATE_FORMAT(NOW(), '%%Y%%m%%d%%H%%i%%s'), %d, 'N'); ",
		 * dto.getMemberSeq(), dto.getAuthUri(), dto.getExpireDtm());
		 */
		String sql = "INSERT INTO forum.member_auth (member_seq, auth_num, auth_uri, reg_dtm, expire_dtm, auth_yn) "
				+ "VALUES(?, '', ?, DATE_FORMAT(NOW(), '%%Y%%m%%d%%H%%i%%s'), ?, 'N'); ";
		Object[] args = {dto.getMemberSeq(), dto.getAuthUri(), dto.getExpireDtm()};
		return update(sql, args);
	}
	
	public MemberAuthDto findMemberAuthByUri(String uri) {
//		String sql = String.format("SELECT * FROM forum.member_auth WHERE auth_uri = '%s'; ", uri);
		String sql = "SELECT * FROM forum.member_auth WHERE auth_uri = ?; ";
		Object[] args = {uri};
		return query(sql, new ResultSetExtractor<MemberAuthDto>() {
			public MemberAuthDto extractData(ResultSet rs) throws SQLException, DataAccessException {
				MemberAuthDto dto = null;
				
				if (rs.next()) {
					dto = new MemberAuthDto();
					dto.setAuthSeq(rs.getInt("auth_seq"));
					dto.setMemberSeq(rs.getInt("member_seq"));
					dto.setAuthNum(rs.getString("auth_num"));
					dto.setAuthUri(rs.getString("auth_uri"));
					dto.setRegDtm(rs.getString("reg_dtm"));
					dto.setExpireDtm(rs.getLong("expire_dtm"));
					dto.setAuthYn(rs.getString("auth_yn"));
				}
				
				return dto;
			}
		}, args);		
	}
	
	public int changeAuthYn(String uri) {
		/*
		 * String sql = String.format("UPDATE forum.member_auth " + "SET auth_yn='Y' " +
		 * "WHERE auth_uri='%s'; ", uri);
		 */
		String sql = "UPDATE forum.member_auth "
				+ "SET auth_yn='Y' "
				+ "WHERE auth_uri=?; ";
		Object[] args = {uri};
		return update(sql, args);
	}
}
