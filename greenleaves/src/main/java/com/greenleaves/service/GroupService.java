package com.greenleaves.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.greenleaves.dao.GroupDao;
import com.greenleaves.model.Group;

@Repository
@Component
public class GroupService {

	@Autowired
	GroupDao gd;
	
	public Group readById(int id) {
		Group g = null;
		try {
			g = gd.groupFindOne("select * from gl_group where id = ?", new Object[] {id});
		}catch(Exception e) {
			return g;
		}
		return g;
	}
	
	public Group readByType(String type) {
		Group g = null;
		try {
			g = gd.groupFindOne("select * from gl_group where type = ?", new Object[] {type});
		}catch(Exception e) {
			return g;
		}
		return g;
	}
	
}
