package com.greenleaves.service;

import com.greenleaves.dao.MemberDao;
import com.greenleaves.model.Group;
import com.greenleaves.model.Member;
import com.greenleaves.tools.DesUtil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

@Repository
@Component
public class MemberService {
	
	@Autowired
	private GroupService gs;
	@Autowired
	private MemberDao md;

	
	//
    public Member readByCookie(String cookie) {
    	int id = cookieDecrypt(cookie);
    	return readById(id);
    }
    
    
    //
    public Member readById(int id) {
    	Member m = md.memberFindOne("select * from gl_member where id = ?", new Object[] {id});
    	m = format(m);
    	return m;
    }
    
    
    // 
    public Member readByMembernamePassword(String name, String password) {
    	password = passwordEncrypt(password);
    	Member m = md.memberFindOne("select * from gl_member where membername = ? and password = ?", new Object[] {name, password});
    	if(m==null) return null;
    	
    	md.memberUpdate(new Object[] {"loginTime"}, new Object[] {System.currentTimeMillis()/1000, m.getId()});
    	return m;
    }
    
    
    // 
    public Member readByMembername(String membername) {
    	Member m = md.memberFindOne("select * from gl_member where membername = ?", new Object[] {membername});
    	return m;
    }
    
    
    // 
    public Member readByEmail(String email) {
    	Member m = md.memberFindOne("select * from gl_member where email = ?", new Object[] {email});
    	return m;
    }
    
    
    //
    public void update(Object[] field, Object[] value) {
    	md.memberUpdate(field, value);
    }
    
    
    //
    public List<Member> getListByGroup(Member m, String group) {
    	String groupType = m.getGroupObj().getType();
    	
    	if(group.equalsIgnoreCase("student")) {
    		if(groupType.equalsIgnoreCase("coordinator")) {
    			return getAllStudents();
    		}else if(groupType.equalsIgnoreCase("supervisor")) {
    			return getSupervisorStudents(m.getId());
    		}
    	}
    	
    	else if(group.equals("supervisor")) {
    		if(groupType.equalsIgnoreCase("coordinator") || groupType.equals("student") || groupType.equals("supervisor")) {
    			return getSupervisors();
    		}
    	}
    	
    	else if(group.equals("reader")) {
    		if(groupType.equalsIgnoreCase("coordinator")) {
    			return getReaders();
    		}
    	}
    	
    	return new ArrayList<Member>();
    }
    
    
    // 
    public void create(String membername, int group, String password, String realName, String email) {
    	password = passwordEncrypt(password);
    	md.memberCreate(membername, group, password, realName, email);
    }
    
    
    //
    public List<Member> getAllStudents() {
    	return md.memberFind("select * from gl_member where `group` = ?", new Object[] {3});
    }
    
    
    // 
    public List<Member> getStudentsOfSupervisor(int id) {
    	return md.memberFind("select * from gl_member where `sid` = ?", new Object[] {id});
    }
    
    
    //
    public List<Member> getSupervisorStudents(int supervisorId) {
    	return md.memberFind("select * from gl_member where `group` = ? and sid = ?", new Object[] {3, supervisorId});
    }
    
    
    //
    public List<Member> getSupervisors() {
    	return md.memberFind("select * from gl_member where `group` = ?", new Object[] {2});
    }
    
    
    //
    public List<Member> getReaders() {
    	return md.memberFind("select * from gl_member where `group` = ?", new Object[] {4});
    }
    
    
    //
    public void updateSupervisor(int mid, int sid) {
    	md.memberUpdate(new Object[] {"sid"}, new Object[] {sid, mid});
    }
    
    
    //
    public List<Group> getGroupList(){
    	return md.memberGroupFind("select * from gl_group", new Object[] {});
    }
    
    
    //
    public Member format(Member m) {
    	if(m==null) return m;
		m.setGroupObj(gs.readById(m.getGroup()));
		return m;
	}
    
    
    //
    public String cookieEncrypt(int id) {
    	return DesUtil.encrypt(id+"#"+System.currentTimeMillis());
    }
    
    
    //
    public int cookieDecrypt(String key) {
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
    
    
    //
    public String passwordEncrypt(String password) {
    	return DigestUtils.md5DigestAsHex(password.getBytes());
    }

}
