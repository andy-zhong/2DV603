package com.greenleaves.application.controller;

import com.greenleaves.application.model.Group;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import com.greenleaves.application.tools.Mysql;

public class GroupController extends BaseController {

	public GroupController(Mysql mysql) {
		super(mysql);
	}
	
	public Group groupRead(int id) {
		Group g = null;
		try {
			g = (Group) mysql.queryOne("select * from gl_group where id = ?", new BeanPropertyRowMapper<Group>(Group.class), new Object[] {id});
		}catch(Exception e) {
			return g;
		}
		return g;
	}
	
	public Group groupRead(String name) {
		Group g = null;
		try {
			g = (Group) mysql.queryOne("select * from gl_group where name = ?", new BeanPropertyRowMapper<Group>(Group.class), new Object[] {name});
		}catch(Exception e) {
			return g;
		}
		return g;
	}
}
