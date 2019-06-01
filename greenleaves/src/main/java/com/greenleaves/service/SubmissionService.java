package com.greenleaves.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.greenleaves.dao.SubmissionDao;
import com.greenleaves.dao.SubmissionTypeDao;
import com.greenleaves.model.Member;
import com.greenleaves.model.Schedule;
import com.greenleaves.model.Submission;
import com.greenleaves.model.SubmissionType;

@Repository
@Component
public class SubmissionService {

	@Autowired
	private SubmissionDao sd;
	@Autowired
	private MemberService ms;
	@Autowired
	private ScheduleService ss;
	@Autowired
	private SubmissionTypeDao std;
	
	
	//
	public String submit(MultipartFile file, String type, Member member, int supervisorId) {
		// Check type
		SubmissionType st = sd.submissionTypeFindOne("select id from gl_submission_type where type = ?", new Object[] {type});
		if(st==null) return "Invalid type";
		// Check supervisor
		if(type.equals("plan")) {
			Member supervisor = ms.readById(supervisorId);
			if(supervisor==null || !supervisor.getGroupObj().getType().equals("supervisor")) return "Please select a supervisor";
		}
		// Check if have submitted
		Schedule schedule = ss.readByType("submit"+type);
		if(schedule==null) return "There is no schedule for this action";
		if(type.equals("description")) {
			Submission submission = sd.submissionFindOne("select * from gl_submission where mid = ? AND type = ? and submitTime >= ? and submitTime <= ?", new Object[]{member.getId(), 1, schedule.getStartTime(), schedule.getEndTime()});
			if(submission!=null) return "You have submitted the description";
		}else if(type.equals("plan")) {
			Submission description = sd.submissionFindOne("select * from gl_submission where mid = ? AND type = ? AND `score` >= ?", new Object[]{member.getId(), 1, 2});
			if(description==null) return "You should submit the description first";
			Submission projectPlan = sd.submissionFindOne("select * from gl_submission where mid = ? AND type = ? and submitTime >= ? and submitTime <= ?", new Object[]{member.getId(), 2, schedule.getStartTime(), schedule.getEndTime()});
			if(projectPlan!=null) return "You have submitted the project plan";
		}else if(type.equals("report")) {
			Submission projectPlan = sd.submissionFindOne("select * from gl_submission where mid = ? AND type = ? AND score >= ?", new Object[]{member.getId(), 2, 2});
			if(projectPlan==null) return "You should submit the project plan first";
			Submission projectReport = sd.submissionFindOne("select * from gl_submission where mid = ? AND type = ? and submitTime >= ? and submitTime <= ?", new Object[]{member.getId(), 3});
			if(projectReport!=null) return "You have submitted the project report";
			supervisorId = projectPlan.getSid();
		}
		// Check file
		if(file.isEmpty()) return "The file is empty";
		if(file.getSize()>1024*1000*10) return "The max size of the file is 10 MB";
		String fileName = file.getOriginalFilename();
		String[] allowedType = {"doc", "docx", "htm", "html", "odt", "pdf", "ppt", "pptx", "rtf", "sxw", "txt", "och", "wps", "zip"};
		boolean allow = false;
		for(int i=0; i<allowedType.length; i++) {
			if(fileName.contains(allowedType[i])) {
				allow = true;
				break;
			}
		}
		if(!allow) return "Invalid file type";
		// Upload file
		String resourcePath = getResourcePath();
		if(resourcePath==null) return null;
		String uploadPath = "upload/";
		File upload = new File(resourcePath + uploadPath);
		if(!upload.isDirectory()) {
			upload.mkdirs();
		}
		uploadPath += Calendar.YEAR + "" + Calendar.MONTH + "" + Calendar.DAY_OF_MONTH + "/";
		upload = new File(resourcePath + uploadPath);
		if(!upload.isDirectory()) {
			upload.mkdir();
		}
		uploadPath += System.currentTimeMillis()/1000 + "-" + fileName.replaceAll(" ", "");
		try {
			Path path = Paths.get(resourcePath + uploadPath);
            Files.write(path, file.getBytes());
		}catch(IOException e) {
			return "Failed to upload";
		}
		// Insert into database
		sd.submissionCreate(member.getId(), supervisorId, st.getId(), uploadPath);
		// Update student's supervisor if submit the plan
		if(type.equalsIgnoreCase("plan")) {
			ms.updateSupervisor(member.getId(), supervisorId);
		}
		return "OK";
	}
	

	//
	public ResponseEntity<FileSystemResource> download(int submissionId) {
		Submission submission = readById(submissionId);
		if(submission==null) return null;
			
		String path = getResourcePath();
		if(path==null) return null;
		File file = new File(path+submission.getAttachPath());
		if(!file.exists()) return null;
		
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	    headers.add("Content-Disposition", "attachment; filename=" + file.getName());
	    headers.add("Pragma", "no-cache");
	    headers.add("Expires", "0");
	    headers.add("Last-Modified", new Date().toString());
	    headers.add("ETag", String.valueOf(System.currentTimeMillis()));
	    return ResponseEntity
        .ok()
        .headers(headers)
        .contentLength(file.length())
        .contentType(MediaType.parseMediaType("application/octet-stream"))
        .body(new FileSystemResource(file));
	}
	
	
	public Submission readById(int id) {
		Submission submission = sd.submissionFindOne("select * from gl_submission where `id` = ?", new Object[] {id});
		return submission;
	}
	
	
	public List<Submission> getListByType(int type) {
		List<Submission> submissions = sd.submissionFind("select * from gl_submission where `type` = ? order by submitTime desc", new Object[] {type});
		return submissions;
	}
	
	
	public List<Submission> getListByTypeStudent(int type, int id) {
		List<Submission> submissions = new ArrayList<Submission>();
		if(type==3) {
			submissions = sd.submissionFind("select * from gl_submission where `type` = ? order by submitTime desc", new Object[] {type});
		}else{
			submissions = sd.submissionFind("select * from gl_submission where `type` = ? and `mid` = ? order by submitTime desc", new Object[] {type, id});
		}
		return submissions;
	}
	
	
	public List<Submission> getListByTypeSupervisor(int type, int id) {
		List<Submission> submissions = sd.submissionFind("select * from gl_submission where `type` = ? and `sid` = ? order by submitTime desc", new Object[] {type, id});
		return submissions;
	}
	
	
	public List<Submission> getDescriptionByMid(int mid) {
		List<Submission> submissions = sd.submissionFind("select * from gl_submission where `type` = ? and mid = ? order by submitTime desc", new Object[] {1, mid});
		return submissions;
	}
	
	
	public List<Submission> studentHasSubmission(int studentId) {
		List<Submission> submissions = sd.submissionFind("select * from gl_submission where mid = ? order by submitTime desc", new Object[] {studentId});
		return submissions;
	}
	
	
	public List<Submission> studentHasDescription(int studentId) {
		List<Submission> submissions = sd.submissionFind("select * from gl_submission where mid = ? and type = ? order by submitTime desc", new Object[] {studentId, 1});
		return submissions;
	}
	
	
	public List<Submission> studentHasProjectPlan(int studentId) {
		List<Submission> submissions = sd.submissionFind("select * from gl_submission where mid = ? and type = ? order by submitTime desc", new Object[] {studentId, 2});
		return submissions;
	}
	
	
	public List<Submission> studentHasProjectReport(int studentId) {
		List<Submission> submissions = sd.submissionFind("select * from gl_submission where mid = ? and type = ? order by submitTime desc", new Object[] {studentId, 3});
		return submissions;
	}
	
	
	public SubmissionType readSubmissionTypeByType(String type) {
		SubmissionType st = std.SubmissionTypeRead("select * from gl_submission_type where type = ?", new Object[] {type});
		return st;
	}
	public SubmissionType readSubmissionTypeById(int id) {
		SubmissionType st = std.SubmissionTypeRead("select * from gl_submission_type where `id` = ?", new Object[] {id});
		return st;
	}
	
	
	private String getResourcePath() {
		URL resourcePath;
		try {
			resourcePath = ResourceUtils.getURL("classpath:");
		} catch (FileNotFoundException e) {
			return null;
		}
		String path = new File(resourcePath.getPath()).getAbsolutePath() + "/static/";
		return path;
	}
	
}
