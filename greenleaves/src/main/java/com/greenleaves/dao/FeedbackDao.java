package com.greenleaves.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.greenleaves.model.Feedback;
import com.greenleaves.tools.Mysql;

@Repository
@Component
public class FeedbackDao {

	@Autowired
	Mysql mysql;
	

	@SuppressWarnings("unchecked")
	public List<Feedback> feedbackFind(String sql, Object[] value) {
		List<Feedback> s = null;
		try {
    		s= (List<Feedback>) mysql.query(sql, new BeanPropertyRowMapper<Feedback>(Feedback.class), value);
		}catch(Exception e) {
			return s;
    	}
		return s;
	}
	
	
	public Feedback feedbackFindOne(String sql, Object[] value) {
		Feedback s = null;
		try {
    		s= (Feedback) mysql.queryOne(sql, new BeanPropertyRowMapper<Feedback>(Feedback.class), value);
		}catch(Exception e) {
			return s;
    	}
		return s;
	}
	
	
	public void feedbackUpdate(Object[] field, Object[] value) {
		String sql = "update gl_feedback set";
		for(int i=0; i<field.length; i++) {
			sql += " "+field[i]+" = ?";
			if(i<field.length-1) sql += ",";
		}
		sql += " where id = ?";
		mysql.update(sql, value);
	}
	
	
	public void feedbackCreate(int memberId, int submissionId, String content, int score) {
		mysql.update("insert into gl_feedback (mid, sid, content, score, createTime) VALUES (?, ?, ?, ?, ?)", new Object[] {memberId, submissionId, content, score, System.currentTimeMillis()/1000});
	}
	
}
