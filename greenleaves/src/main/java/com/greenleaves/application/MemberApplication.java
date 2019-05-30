package com.greenleaves.application;

import com.greenleaves.application.tools.DesUtil;
import com.greenleaves.application.tools.Mysql;
import com.greenleaves.application.tools.Response;

import java.util.List;

import com.greenleaves.application.controller.MemberController;
import com.greenleaves.application.controller.SubmissionController;
import com.greenleaves.application.model.Member;
import org.json.JSONObject;

public class MemberApplication {
	
	private MemberController mc;
	private SubmissionController submissionController;
	
	public MemberApplication(Mysql mysql) {
		mc = new MemberController(mysql);
		submissionController = new SubmissionController(mysql);
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
    
    public String getMemberList(String memberCookie, String group) {
    	List<Member> list = null;
    	try {
			list = mc.getMemberList(memberCookie, group);
			JSONObject[] members = new JSONObject[list.size()];
			for (int i=0; i<members.length; i++) {
				members[i] = new JSONObject(list.get(i).toString());
				if (list.get(i).getGroupObj().getName().equalsIgnoreCase("Student"))
					members[i].put("Submission", getSubmissionStatus(list.get(i).getId()));
				
				else if (list.get(i).getGroupObj().getName().equalsIgnoreCase("Supervisor"))
					members[i].put("Students", getSupervisedStudents(list.get(i).getId()));
				
				else if (list.get(i).getGroupObj().getName().equalsIgnoreCase("Reader"))
					members[i].put("Students", getReaderStudents(list.get(i).getId()));
			}
			
			
			JSONObject jSONObject = new JSONObject();
			jSONObject.put("code", 1);
			jSONObject.put("message", members);
			return jSONObject.toString();
		} catch (IllegalAccessException e) {
			return Response.message(0, e.getMessage());
		}
    }
    
    private String getReaderStudents(int readerId) {
    	List<Member> students = mc.getReaderStudents(readerId);
		if (students == null || students.size() == 0)
			return "";
		
		String s = "";
		for (Member student:students)
			s += student.getRealName() + ", ";
		
    	return s.substring(0, s.length() -3); 
	}

	private String getSupervisedStudents(int supervisorId) {
		List<Member> students = mc.getSupervisedStudents(supervisorId);
		if (students == null || students.size() == 0)
			return "";
		
		String s = "";
		for (Member student:students)
			s += student.getRealName() + ", ";
		
    	return s.substring(0, s.length() -3);
	}

	private String getSubmissionStatus(int studentId) {
    	if (!submissionController.hasPD(studentId))
    		return "-/-/-";
    	
    	else if (!submissionController.hasPP(studentId))
    		return "PD/-/-";
    	
    	else if (!submissionController.hasReport(studentId))
    		return "PD/PP/-";
    	
    	else
    		return "PD/PP/PR";
    }

}
