package com.greenleaves.application.controller;

import com.greenleaves.application.tools.Mysql;

public class BaseController {

	protected Mysql mysql;
	
	public BaseController(Mysql mysql) {
		this.mysql = mysql;
	}
	
}
