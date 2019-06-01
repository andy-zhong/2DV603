package com.greenleaves.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.greenleaves.model.Group;
import com.greenleaves.model.Member;
import com.greenleaves.service.GroupService;
import com.greenleaves.service.MemberService;
import com.greenleaves.tools.Response;

@RestController
public class MemberController {

	@Autowired
	private MemberService ms;
	@Autowired
	private GroupService gs;
	
	
	@RequestMapping(value = "/member/read", method = RequestMethod.POST)
    public Map<String, Object> memberRead(String cookie) {
		Member m = ms.readByCookie(cookie);
		return m==null ? Response.quit() : Response.success(m);
    }
	
	
	@RequestMapping(value = "/member/readById", method = RequestMethod.POST)
    public Map<String, Object> memberReadById(String id) {
		try {
			int memberId = Integer.parseInt(id);
			return Response.success(ms.readById(memberId));
		}catch(Exception e) {
			return Response.fail("Invalid");
		}
    }
	
	
	@RequestMapping(value = "/member/login", method = RequestMethod.POST)
    public Map<String, Object> memberLogin(String name, String password) {
        Member m = ms.readByMembernamePassword(name, password);
        if(m==null) return Response.fail("Invalid membername or password");
        return Response.success(ms.cookieEncrypt(m.getId()));
	}
	
	
	@RequestMapping(value = "/member/list", method = RequestMethod.POST)
    public Map<String, Object> getMemberList(String cookie, String group) {
		Member m = ms.readByCookie(cookie);
		if(m==null) return Response.quit();
		List<Member> list = null;
		try {
			Group groupObj = gs.readByType(group);
			if(groupObj==null) return Response.fail("Invalid");
			list = ms.getListByGroup(m, groupObj.getType());
		}catch(Exception e) {
			return Response.fail("Invalid");
		}
        return Response.success(list);
    }
	
	
	@RequestMapping(value = "/member/edit", method = RequestMethod.POST)
    public Map<String, Object> edit(String cookie, String id, String email, String password) {
		Member m = ms.readByCookie(cookie);
		if(m==null) return Response.quit();
		if(!password.equals(m.getPassword())) password = ms.passwordEncrypt(password);
		try {
			int memberId = Integer.parseInt(id);
			if(!m.getGroupObj().getType().equalsIgnoreCase("coordinator") && memberId!=m.getId())
				return Response.fail("Access denied");
			ms.update(new Object[] {"email", "password"}, new Object[] {email, password, memberId});
			return Response.success("Submitted");
		}catch(Exception e) {
			System.out.println(e);
			return Response.fail("Invalid");
		}
    }
	
	
	@RequestMapping(value = "/member/add", method = RequestMethod.POST)
    public Map<String, Object> add(String cookie, String membername, String password, String email, String realName, String group) {
		Member m = ms.readByCookie(cookie);
		if(m==null) return Response.quit();
		if(!m.getGroupObj().getType().equalsIgnoreCase("coordinator")) return Response.fail("Access denied");
		if(membername.equals("") || password.equals("") || realName.equals("") || email.equals("")) return Response.fail("Please fill all the infomation");
		if(ms.readByMembername(membername)!=null) return Response.fail("Duplicate Member Name");
		if(ms.readByEmail(email)!=null) return Response.fail("Duplicate Email");
		try {
			int groupId = Integer.parseInt(group);
			Group groupObj = gs.readById(groupId);
			if(groupObj==null) return Response.fail("Invalid Group");
			ms.create(membername, groupId, password, realName, email);
			return Response.success("Submitted");
		}catch(Exception e) {
			System.out.println(e);
			return Response.fail("Invalid");
		}
    }
	
	
	@RequestMapping(value = "/member/group", method = RequestMethod.POST)
    public Map<String, Object> group() {
        return Response.success(ms.getGroupList());
    }
	
	
	@RequestMapping(value = "/member/studentListOfSupervisor", method = RequestMethod.POST)
    public Map<String, Object> studentListOfSupervisor(String id) {
		List<Member> list = null;
		try {
			int memberId = Integer.parseInt(id);
			list = ms.getStudentsOfSupervisor(memberId);
		}catch(Exception e) {
			return Response.fail("Invalid");
		}
        return Response.success(list);
    }
	
}
