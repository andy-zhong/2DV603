package com.greenleaves.application;

import org.springframework.web.multipart.MultipartFile;

import com.greenleaves.application.controller.SubmissionController;
import com.greenleaves.application.tools.Mysql;
import com.greenleaves.application.tools.Response;

public class SubmissionApplication {

	private SubmissionController sc;
	
	public SubmissionApplication(Mysql mysql) {
		sc = new SubmissionController(mysql);
	}
	
	public String submit(MultipartFile file, String type, String memberCookie, String supervisorId) {
		String res = sc.submit(file, type, memberCookie, supervisorId);
		return res.equals("OK") ? Response.message(1, "Submit successfully") : Response.message(0, res);
	}
	
}
