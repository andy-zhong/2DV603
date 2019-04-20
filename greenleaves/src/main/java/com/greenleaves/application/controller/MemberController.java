package com.greenleaves.application.controller;

import com.greenleaves.application.model.Member;

import com.greenleaves.application.tools.DesUtil;
import com.greenleaves.application.tools.Mysql;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.util.DigestUtils;

public class MemberController extends BaseController {

	public MemberController(Mysql mysql) {
		super(mysql);
	}

	public Member memberRead(int id) {
		Member m = null;
		try {
    		m = (Member) mysql.queryOne("select * from gl_member where id = ?", new BeanPropertyRowMapper<Member>(Member.class), new Object[] {id});
		}catch(Exception e) {
    		return m;
    	}
		m = memberFormat(m);
		return m;
	}
	
	public Member memberLogin(String name, String password) {
		password = DigestUtils.md5DigestAsHex(password.getBytes());
    	Member m = null;
		try {
    		m = (Member) mysql.queryOne("select * from gl_member where memberName = ? and password = ?", new BeanPropertyRowMapper<Member>(Member.class), new Object[] {name, password});
    		mysql.update("update gl_member set loginTime = ? where id = ?", new Object[] {System.currentTimeMillis()/1000, m.getId()});
		}catch(Exception e) {
    		return m;
    	}
		return m;
	}
	
	public Member memberFormat(Member m) {
		GroupController gc = new GroupController(mysql);
		m.setGroupObj(gc.groupRead(m.getGroup()));
		return m;
	}
	
	public int memberDecrypt(String key) {
		int id = -1;
		key = DesUtil.decrypt(key);
    	if(key==null || key.equals("")) {
    		return id;
    	}
    	try {
			String[] arr = key.split("#");
			if(arr.length!=2 || Long.parseLong(arr[1])<System.currentTimeMillis()-86400*1000) {
				return id;
			}
			id = Integer.parseInt(arr[0]);
    	}catch(Exception e) {
    		return id;
    	}
    	return id;
	}
	
}
