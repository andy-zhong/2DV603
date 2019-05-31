package com.greenleaves.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.greenleaves.model.Submission;
import com.greenleaves.model.SubmissionType;
import com.greenleaves.tools.Mysql;

@Repository
@Component
public class SubmissionDao {

	@Autowired
	Mysql mysql;
	

	@SuppressWarnings("unchecked")
	public List<Submission> submissionFind(String sql, Object[] value) {
		List<Submission> s = null;
		try {
    		s= (List<Submission>) mysql.query(sql, new BeanPropertyRowMapper<Submission>(Submission.class), value);
		}catch(Exception e) {
			return s;
    	}
		return s;
	}
	
	
	public Submission submissionFindOne(String sql, Object[] value) {
		Submission s = null;
		try {
    		s= (Submission) mysql.queryOne(sql, new BeanPropertyRowMapper<Submission>(Submission.class), value);
		}catch(Exception e) {
			return s;
    	}
		return s;
	}
	
	
	public SubmissionType submissionTypeFindOne(String sql, Object[] value) {
		SubmissionType st = null;
		try {
			st = (SubmissionType) mysql.queryOne(sql, new BeanPropertyRowMapper<SubmissionType>(SubmissionType.class), value);
		}catch(Exception e) {
			return st;
		}
		return st;
	}
	
	
	public void submissionUpdate(Object[] field, Object[] value) {
		String sql = "update gl_submission set";
		for(int i=0; i<field.length; i++) {
			sql += " "+field[i]+" = ?";
			if(i<field.length-1) sql += ",";
		}
		sql += " where id = ?";
		mysql.update(sql, value);
	}
	
	
	public void submissionCreate(int memberId, int supervisorId, int type, String attachPath) {
		mysql.update("insert into gl_submission (mid, sid, type, attachPath, submitTime, gradeTime) VALUES (?, ?, ?, ?, ?, ?)", new Object[] {memberId, supervisorId, type, attachPath, System.currentTimeMillis()/1000, 0});
	}
	
}
