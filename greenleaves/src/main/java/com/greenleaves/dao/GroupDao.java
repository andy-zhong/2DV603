package com.greenleaves.dao;

import com.greenleaves.model.Group;
import com.greenleaves.tools.Mysql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public class GroupDao {
	
	@Autowired
	Mysql mysql;
	
	@SuppressWarnings("unchecked")
	public List<Group> groupFind(String sql, Object[] value) {
		List<Group> list = new ArrayList<Group>();
		try {
			list = (List<Group>) mysql.query(sql, new BeanPropertyRowMapper<Group>(Group.class), value);
		}catch(Exception e) {
			return list;
		}
		return list;
	}
	
	
	public Group groupFindOne(String sql, Object[] value) {
		Group m = null;
		try {
			m = (Group) mysql.queryOne(sql, new BeanPropertyRowMapper<Group>(Group.class), value);
		}catch(Exception e) {
			return m;
		}
		return m;
	}
	
	
	public void groupUpdate(Object[] field, Object[] value) {
		String sql = "update gl_group set";
		for(int i=0; i<field.length; i++) {
			sql += " "+field[i]+" = ?";
			if(i<field.length-1) sql += " and";
		}
		sql += " where id = ?";
		mysql.update(sql, value);
	}

}
