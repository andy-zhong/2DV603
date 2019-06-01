package com.greenleaves.application.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.multipart.MultipartFile;

import com.greenleaves.application.model.Member;
import com.greenleaves.application.model.Submission;
import com.greenleaves.application.model.SubmissionType;
import com.greenleaves.application.tools.Mysql;

public class SubmissionController extends BaseController {

	public SubmissionController(Mysql mysql) {
		super(mysql);
	}
	
	public String submit(MultipartFile file, String type, String memberCookie, String supervisorId) {
		SubmissionTypeController stc = new SubmissionTypeController(mysql);
		MemberController mc = new MemberController(mysql);
		// Check authority
		int memberId = mc.memberDecrypt(memberCookie);
		if(memberId==-1) return "Invalid memberHahahaha";
		Member member = mc.memberRead(memberId);
		if(member==null || !member.getGroupObj().getType().equals("student")) return "Invalid member";
		// Check type
		SubmissionType st = stc.SubmissionTypeReadByType(type);
		if(st==null) return "Invalid type";
		// Check supervisor
		int sid = 0;
		if(type.equals("projectPlan")) {
			try {
				sid = Integer.parseInt(supervisorId);
				Member supervisor = mc.memberRead(sid);
				if(supervisor==null || !supervisor.getGroupObj().getType().equals("supervisor")) return "Please select a supervisor";
			}catch(Exception e) {
				return "Invalid supervisor";
			}
		}
		// Check if have submitted
		if(type.equals("description")) {
			List<Submission> submissionList = submissionFind(" where mid = ? AND type = ?", new Object[]{memberId, 1});
			if(submissionList!=null && submissionList.size()>0) return "You have submitted the description";
		}else if(type.equals("projectPlan")) {
			List<Submission> descriptionList = submissionFind(" where mid = ? AND type = ? AND score >= ?", new Object[]{memberId, 1, 2});
			if(descriptionList==null || descriptionList.size()==0) return "You should submit the description first";
			List<Submission> projectPlanList = submissionFind(" where mid = ? AND type = ?", new Object[]{memberId, 2});
			if(projectPlanList!=null && projectPlanList.size()>0) return "You have submitted the project plan";
		}else if(type.equals("projectReport")) {
			List<Submission> projectPlanList = submissionFind(" where mid = ? AND type = ? AND score >= ?", new Object[]{memberId, 2, 2});
			if(projectPlanList!=null && projectPlanList.size()==0) return "You should submit the project plan first";
			List<Submission> projectReportList = submissionFind(" where mid = ? AND type = ?", new Object[]{memberId, 3});
			if(projectReportList!=null && projectReportList.size()>0) return "You have submitted the project report";
			sid = projectPlanList.get(0).getSid();
		}
		// Check file
		if(file.isEmpty()) return "The file is empty";
		if(file.getSize()>1024*1000*10) return "The max size of the file is 10 MB";
		String fileName = file.getOriginalFilename();
		String[] allowedType = {"doc", "docx", "htm", "html", "odt", "pdf", "ppt", "pptx", "rtf", "sxw", "txt", "och", "wps"};
		boolean allow = false;
		for(int i=0; i<allowedType.length; i++) {
			if(fileName.contains(allowedType[i])) {
				allow = true;
				break;
			}
		}
		if(!allow) return "Invalid file type";
		// Upload file
		String uploadPath = "upload/";
		File upload = new File(uploadPath);
		if(!upload.isDirectory()) {
			upload.mkdirs();
		}
		uploadPath += Calendar.YEAR + "" + Calendar.MONTH + "" + Calendar.DAY_OF_MONTH + "/";
		upload = new File(uploadPath);
		if(!upload.isDirectory()) {
			upload.mkdir();
		}
		uploadPath += System.currentTimeMillis()/1000 + "#" + fileName;
		try {
			Path path = Paths.get(uploadPath);
            Files.write(path, file.getBytes());
		}catch(IOException e) {
			return "Failed to upload";
		}
		// Insert into database
		create(memberId, sid, st.getId(), uploadPath);
		return "OK";
	}
	
	// Find submission
	@SuppressWarnings("unchecked")
	public List<Submission> submissionFind(String sql, Object[] value) {
		List<Submission> s = null;
		try {
    		s= (List<Submission>) mysql.query("select * from gl_submission" + sql, new BeanPropertyRowMapper<Submission>(Submission.class), value);
		}catch(Exception e) {
			return s;
    	}
		return s;
	}
	
	public boolean hasPD(int studentId) {
		List<Submission> submissions = submissionFind(" where mid = ? and type = ?", new Object[] {studentId, 1});
		if (submissions == null || submissions.size() == 0)
			return false;
		
		else
			return true;
	}
	
	public boolean hasPP(int studentId) {
		List<Submission> submissions = submissionFind(" where mid = ? and type = ?", new Object[] {studentId, 2});
		if (submissions == null || submissions.size() == 0)
			return false;
		
		else
			return true;
	}
	
	public boolean hasReport(int studentId) {
		List<Submission> submissions = submissionFind(" where mid = ? and type = ?", new Object[] {studentId, 3});
		if (submissions == null || submissions.size() == 0)
			return false;
		
		else
			return true;
	}
	
	
	
	// Create Submission
	public void create(int memberId, int supervisorId, int type, String attachPath) {
		mysql.update("insert into gl_submission (mid, sid, type, attachPath, submitTime, gradeTime) VALUES (?, ?, ?, ?, ?, ?)", new Object[] {memberId, supervisorId, type, attachPath, System.currentTimeMillis()/1000, 0});
	}
	
}
