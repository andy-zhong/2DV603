package com.greenleaves.model;

public class Member {

	private int id;
	private String memberName;
	private String password;
	private int group;
	private String email;
	private String realName;
	private int loginTime;
	private Group groupObj;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMembername() {
		return memberName;
	}
	public void setMembername(String memberName) {
		this.memberName = memberName;
	}
	
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	
	public int getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(int loginTime) {
		this.loginTime = loginTime;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Group getGroupObj() {
		return groupObj;
	}
	
	public void setGroupObj(Group groupArr) {
		this.groupObj = groupArr;
	}
	
	public String getRealName() {
		return realName;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
