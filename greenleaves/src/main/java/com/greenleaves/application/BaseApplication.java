package com.greenleaves.application;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.greenleaves.application.tools.Mysql;

@SpringBootApplication
@EnableAutoConfiguration
@Controller
@CrossOrigin("http://localhost:3000")
public class BaseApplication {

	@Autowired
	public Mysql mysql;
	
	private IndexApplication indexApp;
	private MemberApplication memberApp;
	private SubmissionApplication submissionApp;
	
	
	/***************************** Index *****************************/
	
	@RequestMapping("/")
    @ResponseBody
    public String index() {
		indexApp = new IndexApplication();
        return indexApp.index();
    }
	
	
	/***************************** Member *****************************/
	
	@RequestMapping(value = "/member/read", method = RequestMethod.POST)
    @ResponseBody
    public String memberRead(String cookie) {
		
		memberApp = new MemberApplication(mysql);
		return memberApp.read(cookie);
    }
	
	@RequestMapping(value = "/member/login", method = RequestMethod.POST)
    @ResponseBody
    public String memberLogin(String name, String password) {
		memberApp = new MemberApplication(mysql);
        return memberApp.login(name, password);
    }
	
	
	/***************************** Member *****************************/
	
	// String group parameter: "student" will get the student list. "supervisor" will get the supervisor list. "Coordinator" will get the coordinator list. "Reader" will get the readers list
	@RequestMapping(value = "/member/list", method = RequestMethod.POST)
    @ResponseBody
    public String getMemberList(String memberCookie, String group) {
		memberApp = new MemberApplication(mysql);
        return memberApp.getMemberList(memberCookie, group);
    }
	
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public String submit(@RequestParam("file") MultipartFile file, String type, String member, String sid) {
		submissionApp = new SubmissionApplication(mysql);
		return submissionApp.submit(file, type, member, sid);
    }
	
	/***************************** Run *****************************/
	
	public static void main(String[] args) {
		SpringApplication.run(BaseApplication.class, args);
	}
	
}
