package com.greenleaves.application.controller;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.greenleaves.application.model.SubmissionType;
import com.greenleaves.application.tools.Mysql;

public class SubmissionTypeController extends BaseController {

	public SubmissionTypeController(Mysql mysql) {
		super(mysql);
	}
	
	public SubmissionType SubmissionTypeReadByType(String type) {
		SubmissionType st = null;
		try {
    		st= (SubmissionType) mysql.queryOne("select * from gl_submission_type where type = ?", new BeanPropertyRowMapper<SubmissionType>(SubmissionType.class), new Object[] {type});
		}catch(Exception e) {
    		return st;
    	}
		return st;
	}
	
}
