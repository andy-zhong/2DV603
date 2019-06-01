package com.greenleaves.dao;

import com.greenleaves.model.Group;
import com.greenleaves.model.Member;
import com.greenleaves.tools.Mysql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public class MemberDao {
	
	@Autowired
	Mysql mysql;
	
	@SuppressWarnings("unchecked")
	public List<Member> memberFind(String sql, Object[] value) {
		List<Member> list = new ArrayList<Member>();
		try {
			list = (List<Member>) mysql.query(sql, new BeanPropertyRowMapper<Member>(Member.class), value);
		}catch(Exception e) {
			return list;
		}
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Group> memberGroupFind(String sql, Object[] value) {
		List<Group> list = new ArrayList<Group>();
		try {
			list = (List<Group>) mysql.query(sql, new BeanPropertyRowMapper<Group>(Group.class), value);
		}catch(Exception e) {
			return list;
		}
		return list;
	}
	
	
	public Member memberFindOne(String sql, Object[] value) {
		Member m = null;
		try {
			m = (Member) mysql.queryOne(sql, new BeanPropertyRowMapper<Member>(Member.class), value);
		}catch(Exception e) {
			return m;
		}
		return m;
	}
	
	
	public void memberUpdate(Object[] field, Object[] value) {
		String sql = "update gl_member set";
		for(int i=0; i<field.length; i++) {
			sql += " "+field[i]+" = ?";
			if(i<field.length-1) sql += ",";
		}
		sql += " where id = ?";
		mysql.update(sql, value);
	}
	
	
	public void memberCreate(String membername, int group, String password, String realName, String email) {
		mysql.update("insert into gl_member (membername, `group`, password, realName, email) VALUES (?, ?, ?, ?, ?)", new Object[] {membername, group, password, realName, email});
	}

}
