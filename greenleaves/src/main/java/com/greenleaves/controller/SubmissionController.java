package com.greenleaves.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.greenleaves.model.Member;
import com.greenleaves.model.Submission;
import com.greenleaves.model.SubmissionType;
import com.greenleaves.service.MemberService;
import com.greenleaves.service.SubmissionService;
import com.greenleaves.tools.Response;

@RestController
public class SubmissionController {

	@Autowired
	private SubmissionService ss;
	@Autowired
	private MemberService ms;
	@Autowired  
    ResourceLoader loader;  
	
	
	@RequestMapping(value = "/submission/submit", method = RequestMethod.POST)
    public Map<String, Object> submit(@RequestParam("file") MultipartFile file, String type, String cookie, String supervisorId) {
		Member m = ms.readByCookie(cookie);
		if(m==null) return Response.quit();
		if(!m.getGroupObj().getType().equalsIgnoreCase("student")) return Response.fail("Only student can submit the document");
		int sid = 0;
		try {
			sid = Integer.parseInt(supervisorId);
		}catch(Exception e) {
			sid = 0;
		}
		String res = ss.submit(file, type, m, sid);
		return res.equalsIgnoreCase("ok") ? Response.success("Upload successfully") : Response.fail(res);
    }
	
	
	@RequestMapping("/submission/download")
	@ResponseBody
	public ResponseEntity<FileSystemResource> download(String cookie, String id) {
		Member m = ms.readByCookie(cookie);
		if(m==null) return null;
		try {
			int submissionId = Integer.parseInt(id);
			return ss.download(submissionId);
		}catch(Exception e) {
			return null;
		}
    }  
	
	
	@RequestMapping(value = "/submission/list", method = RequestMethod.POST)
	public Map<String, Object> list(String cookie, String type) {
		Member m = ms.readByCookie(cookie);
		if(m==null) return null;
		SubmissionType st = ss.readSubmissionTypeByType(type);
		String group = m.getGroupObj().getType();
		List<Submission> list = new ArrayList<Submission>();
		if(group.equalsIgnoreCase("coordinator") || group.equalsIgnoreCase("reader")) {
			list = ss.getListByType(st.getId());
		}else if(group.equalsIgnoreCase("student")) {
			list = ss.getListByTypeStudent(st.getId(), m.getId());
		}else if(group.equalsIgnoreCase("supervisor")) {
			list = ss.getListByTypeSupervisor(st.getId(), m.getId());
		}
		return Response.success(list);
    } 
	
	
	@RequestMapping(value = "/submission/read", method = RequestMethod.POST)
	public Map<String, Object> read(String cookie, String id) {
		Member m = ms.readByCookie(cookie);
		if(m==null) return null;
		try {
			int submissionId = Integer.parseInt(id);
			Submission submission = ss.readById(submissionId);
			if(m.getGroupObj().getType().equalsIgnoreCase("student") && submission.getMid()!=m.getId())
				return Response.fail("Access denied");
			return Response.success(submission);
		}catch(Exception e) {
			return Response.fail("Invalid param");
		}
    } 
	
	
	@RequestMapping(value = "/submission/listOfStudent", method = RequestMethod.POST)
    public Map<String, Object> listOfStudent(String cookie, String type) {
		Member m = ms.readByCookie(cookie);
		if(m==null) return Response.quit();
		if(!m.getGroupObj().getType().equalsIgnoreCase("student") && 
				!m.getGroupObj().getType().equalsIgnoreCase("coordinator")) 
			return Response.fail("Access denied");
		List<Submission> list = new ArrayList<Submission>();
		if(type.equalsIgnoreCase("description")) {
			list = ss.studentHasDescription(m.getId());
		}else if(type.equalsIgnoreCase("plan")) {
			list = ss.studentHasProjectPlan(m.getId());
		}else if(type.equalsIgnoreCase("report")) {
			list = ss.studentHasProjectReport(m.getId());
		}else {
			list = ss.studentHasSubmission(m.getId());
		}
		return Response.success(list);
    }
	
	
	@RequestMapping(value = "/submission/listByStudent", method = RequestMethod.POST)
    public Map<String, Object> listByStudent(String cookie, String id) {
		Member m = ms.readByCookie(cookie);
		if(m==null) return Response.quit();
		if(!m.getGroupObj().getType().equalsIgnoreCase("supervisor") && 
				!m.getGroupObj().getType().equalsIgnoreCase("coordinator")) 
			return Response.fail("Access denied");
		List<Submission> list = new ArrayList<Submission>();
		try {
			int memberId = Integer.parseInt(id);
			list = ss.studentHasSubmission(memberId);
		}catch(Exception e) {
			return Response.fail("Invalid");
		}
		return Response.success(list);
    }

	
}
