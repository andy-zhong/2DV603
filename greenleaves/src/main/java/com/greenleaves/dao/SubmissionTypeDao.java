package com.greenleaves.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.greenleaves.model.SubmissionType;
import com.greenleaves.tools.Mysql;

@Repository
@Component
public class SubmissionTypeDao {

	@Autowired
	Mysql mysql;
	
	public SubmissionType SubmissionTypeRead(String sql, Object[] value) {
		SubmissionType st = null;
		try {
    		st= (SubmissionType) mysql.queryOne(sql, new BeanPropertyRowMapper<SubmissionType>(SubmissionType.class), value);
		}catch(Exception e) {
    		return st;
    	}
		return st;
	}
	
}
