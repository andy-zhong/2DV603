package com.greenleaves.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.greenleaves.model.Member;
import com.greenleaves.service.MemberService;
import com.greenleaves.service.FeedbackService;
import com.greenleaves.tools.Response;

@RestController
public class FeedbackController {

	@Autowired
	private FeedbackService fs;
	@Autowired
	private MemberService ms;
	
	
	@RequestMapping(value = "/feedback/list", method = RequestMethod.POST)
    public Map<String, Object> list(String cookie, String id) {
		Member m = ms.readByCookie(cookie);
		if(m==null) return Response.quit();
		try {
			int submissionId = Integer.parseInt(id);
			return Response.success(fs.getListBySubmissionId(submissionId));
		}catch(Exception e) {
			return Response.fail("Invalid ID");
		}
    }
	
	
	@RequestMapping(value = "/feedback/submit", method = RequestMethod.POST)
    public Map<String, Object> submit(String cookie, String id, String score, String content) {
		Member m = ms.readByCookie(cookie);
		if(m==null) return Response.quit();
		try {
			int submissionId = Integer.parseInt(id);
			int grade = Integer.parseInt(score);
			String res = fs.submit(m, submissionId, grade, content);
			return res.equalsIgnoreCase("ok") ? Response.success("Submit successfully") : Response.fail(res);
		}catch(Exception e) {
			System.out.println(e);
			return Response.fail("Invalid param");
		}
    }
	
}
