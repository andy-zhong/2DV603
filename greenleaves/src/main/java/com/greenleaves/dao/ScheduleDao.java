package com.greenleaves.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.greenleaves.model.Schedule;
import com.greenleaves.tools.Mysql;

@Repository
@Component
public class ScheduleDao {

	@Autowired
	Mysql mysql;


	@SuppressWarnings("unchecked")
	public List<Schedule> scheduleFind(String sql, Object[] value) {
		List<Schedule> schedule = new ArrayList<Schedule>();
		try {
			schedule = (List<Schedule>) mysql.query(sql, new BeanPropertyRowMapper<Schedule>(Schedule.class), value);
		}catch(Exception e) {
			return schedule;
		}
		return schedule;
	}
	
	
	public Schedule scheduleFindOne(String sql, Object[] value) {
		Schedule schedule = null;
		try {
			schedule = (Schedule) mysql.queryOne(sql, new BeanPropertyRowMapper<Schedule>(Schedule.class), value);
		}catch(Exception e) {
			return schedule;
		}
		return schedule;
	}
	
	
	public void scheduleUpdate(Object[] field, Object[] value) {
		String sql = "update gl_schedule set";
		for(int i=0; i<field.length; i++) {
			sql += " "+field[i]+" = ?";
			if(i<field.length-1) sql += ",";
		}
		sql += " where id = ?";
		mysql.update(sql, value);
	}
	
	
	public void scheduleCreate(String title, String type, int startTime, int endTime) {
		mysql.update("insert into gl_schedule (title, type, startTime, endTime) VALUES (?, ?, ?, ?)", new Object[] {title, type, startTime, endTime});
	}
	
}

