package com.greenleaves.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.greenleaves.model.Member;
import com.greenleaves.model.Schedule;
import com.greenleaves.service.MemberService;
import com.greenleaves.service.ScheduleService;
import com.greenleaves.tools.Response;

@RestController
public class ScheduleController {
	
	@Autowired
	ScheduleService ss;
	@Autowired
	private MemberService ms;

	
	@RequestMapping(value = "/schedule/list", method = RequestMethod.POST)
	public Map<String, Object> list() {
		return Response.success(ss.getList());
	}
	
	
	@RequestMapping(value = "/schedule/read", method = RequestMethod.POST)
	public Map<String, Object> getSchedule(String type) {
		return Response.success(ss.readByType(type));
	}
	
	
	@RequestMapping(value = "/schedule/readById", method = RequestMethod.POST)
	public Map<String, Object> getScheduleById(String id) {
		try {
			int scheduleId = Integer.parseInt(id);
			return Response.success(ss.readById(scheduleId));
		}catch(Exception e) {
			return Response.fail("Invalid");
		}
	}
	
	
	@RequestMapping(value = "/schedule/edit", method = RequestMethod.POST)
	public Map<String, Object> edit(String cookie, String id, String title, String type, String startTime, String endTime) {
		Member m = ms.readByCookie(cookie);
		if(m==null) return Response.quit();
		if(!m.getGroupObj().getType().equalsIgnoreCase("coordinator")) return Response.fail("Access denied");
		try {
			int scheduleId = Integer.parseInt(id);
			Schedule schedule = ss.readById(scheduleId);
			if(schedule==null) return Response.fail("Invalid");
			int stime = Integer.parseInt(startTime);
			int etime = Integer.parseInt(endTime);
			ss.update(new Object[] {"title", "type", "startTime", "endTime"}, new Object[] {title, type, stime, etime, scheduleId}); 
			return Response.success("Changed");
		}catch(Exception e) {
			return Response.fail("Invalid");
		}
	}
	
	
	@RequestMapping(value = "/schedule/add", method = RequestMethod.POST)
	public Map<String, Object> add(String cookie, String title, String type, String startTime, String endTime) {
		Member m = ms.readByCookie(cookie);
		if(m==null) return Response.quit();
		if(!m.getGroupObj().getType().equalsIgnoreCase("coordinator")) return Response.fail("Access denied");
		try {
			int stime = Integer.parseInt(startTime);
			int etime = Integer.parseInt(endTime);
			ss.create(title, type, stime, etime);
			return Response.success("Changed");
		}catch(Exception e) {
			System.out.println(e);
			return Response.fail("Invalid");
		}
	}
	
}
