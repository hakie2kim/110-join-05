package com.portfolio.www.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.portfolio.www.dto.MemberAuthDto;

public class MemberAuthDao extends JdbcTemplate {
	private DataSource dataSource;
	
	public int addMemberAuthInfo(MemberAuthDto dto) {
		String sql = String.format("INSERT INTO forum.member_auth "
				+ "(member_seq, auth_num, auth_uri, reg_dtm, expire_dtm, auth_yn) "
				+ "VALUES(%d, '', '%s', DATE_FORMAT(NOW(), '%%Y%%m%%d%%H%%i%%s'), %d, 'N'); ", dto.getMemberSeq(), dto.getAuthUri(), dto.getExpireDtm());
		return update(sql);
	}
}
