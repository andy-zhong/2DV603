package com.greenleaves.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.greenleaves.dao.ScheduleDao;
import com.greenleaves.model.Schedule;

@Repository
@Component
public class ScheduleService {

	@Autowired
	ScheduleDao sd;
	
	
	public List<Schedule> getList() {
		return sd.scheduleFind("select * from gl_schedule order by startTime desc", new Object[] {});
	}
	
	
	public Schedule readByType(String type) {
		long time = System.currentTimeMillis() / 1000;
		Schedule sc = sd.scheduleFindOne("select * from gl_schedule where startTime <= ? and endTime >= ? and type = ?", new Object[] {time, time, type});
		return sc;
	}
	
	
	public Schedule readById(int id) {
		Schedule sc = sd.scheduleFindOne("select * from gl_schedule where id = ?", new Object[] {id});
		return sc;
	}
	
	
	public void update(Object[] field, Object[] value) {
		sd.scheduleUpdate(field, value);
	}
	
	
	public void create(String title, String type, int startTime, int endTime) {
		sd.scheduleCreate(title, type, startTime, endTime);
	}
	
}
