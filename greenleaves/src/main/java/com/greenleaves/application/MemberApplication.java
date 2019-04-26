package com.greenleaves.application;

import com.greenleaves.application.tools.DesUtil;
import com.greenleaves.application.tools.Mysql;
import com.greenleaves.application.tools.Response;
import com.greenleaves.application.controller.MemberController;
import com.greenleaves.application.model.Member;

public class MemberApplication {
	
	private MemberController mc;
	
	public MemberApplication(Mysql mysql) {
		mc = new MemberController(mysql);
	}
	
    public String read(String cookie) {
    	int id = mc.memberDecrypt(cookie);
    	Member m = mc.memberRead(id);
		return m!=null ? Response.message(1, m.toString()) : Response.message(0, "");
    }
    
    public String login(String name, String password) {
    	Member m = mc.memberLogin(name, password);
    	return m!=null ? Response.message(1, DesUtil.encrypt(m.getId()+"#"+System.currentTimeMillis())) : Response.message(0, "Invalid member name or password");
    }

}
