package com.greenleaves.application.controller;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.greenleaves.application.model.Schedule;
import com.greenleaves.application.tools.Mysql;

public class ScheduleController extends BaseController {

	public ScheduleController(Mysql mysql) {
		super(mysql);
		
	}

	public Schedule scheduleRead(int id) {
		Schedule sc = null;
		try {
    		sc = (Schedule) mysql.queryOne("select * from gl_schedule where id = ?", new BeanPropertyRowMapper<Schedule>(Schedule.class), new Object[] {id});
		}catch(Exception e) {
    		return sc;
    	}
		return sc;
	}
	public List<Schedule> scheduleList(){
		List<Schedule>  sc = null;
		try {
    		sc = (List<Schedule> ) mysql.query("select * from gl_schedule", new  BeanPropertyRowMapper(), new Object[] {} );
		}catch(Exception e) {
    		return sc;
    	}
		return sc;
	}
	}

