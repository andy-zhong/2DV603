package com.greenleaves.application;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class IndexApplication extends BaseApplication {
	
	@RequestMapping("/")
    @ResponseBody
    public String index() {
        return "Hello World!";
    }
	
	@RequestMapping("/index/test")
    @ResponseBody
    public String test() {
        return "Well done!";
    }

	public static void main(String[] args) {
		SpringApplication.run(IndexApplication.class, args);
	}

}
