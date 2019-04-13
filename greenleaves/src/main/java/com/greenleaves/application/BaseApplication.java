package com.greenleaves.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

import com.greenleaves.application.tools.Mysql;

@EnableAutoConfiguration
@SpringBootApplication
@Controller
public class BaseApplication {

	@Autowired
	protected Mysql mysql;
	
}
