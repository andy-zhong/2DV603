package com.greenleaves.application.controller;

import com.greenleaves.application.model.Group;
import com.greenleaves.application.model.Member;
import com.greenleaves.application.tools.DesUtil;
import com.greenleaves.application.tools.Mysql;

import java.util.ArrayList;
import java.util.List;

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

	@SuppressWarnings("unchecked")
	public List<Member> getMemberList(String memberCookie, String groupList) throws IllegalAccessException {
		
		String authorizationMessage = checkListAuthority(memberCookie, groupList);
		if (!authorizationMessage.equalsIgnoreCase("OK"))
			throw new IllegalAccessException(authorizationMessage);
		
		String query = "SELECT * FROM gl_member";
		Object[] data = new Object[] {};
		GroupController gc = new GroupController(mysql);
		if (groupList != null && !groupList.equals("")) {
			Group groupListObject = gc.groupRead(groupList);
			query += " WHERE `group`= ?";
			data = new Object[] {groupListObject.getId()};
		}
	
		List<Member> memberList = null;
		try {
			memberList = (List<Member>) mysql.query(query, new BeanPropertyRowMapper<Member>(Member.class), data);
		}
		
		catch (Exception e) {
			return memberList;
		}
		
		for (Member member:memberList)
			member.setGroupObj(gc.groupRead(member.getGroup()));
			
		return memberList;
	}

	private String checkListAuthority(String memberCookie, String groupList) {
		int userId = memberDecrypt(memberCookie);
		if (userId == -1)
			return "Invalid member";
		Member user = memberRead(userId);
		String userGroup = user.getGroupObj().getName();
		
		if (groupList != null && !groupList.equalsIgnoreCase("")) {
			if (groupList.equalsIgnoreCase("Student") && !(userGroup.equalsIgnoreCase("Coordinator") || userGroup.equalsIgnoreCase("Supervisor")))
				return "Access denied";
			
			else if (groupList.equalsIgnoreCase("Reader") && !(userGroup.equalsIgnoreCase("Coordinator") || userGroup.equalsIgnoreCase("Reader")))
				return "Access denied";
			
			else {
				return "OK";
			}
		}
		
		else if (!userGroup.equalsIgnoreCase("Coordinator"))
				return "Access denied";
		
		return "OK";
	}
	
	@SuppressWarnings("unchecked")
	public List<Member> getSupervisedStudents(int supervisorId) {
		List<Member> supervisedStudents = null;
		try {
			supervisedStudents = (List<Member>) mysql.query("select distinct gl_member.* from gl_member, gl_submission where gl_submission.sid = ? and gl_submission.mid = gl_member.id", new BeanPropertyRowMapper<Member>(Member.class), new Object[] {supervisorId});
			List<Member> formatted = new ArrayList<Member>();
			
			for (Member member:supervisedStudents)
				formatted.add(memberFormat(member));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supervisedStudents;
	}

	public List<Member> getReaderStudents(int readerId) {
		return null;
	}
}
