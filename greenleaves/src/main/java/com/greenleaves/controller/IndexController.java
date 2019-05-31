package com.greenleaves.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenleaves.service.IndexService;

@RestController
public class IndexController {
	
	@Autowired
	private IndexService is;

	@RequestMapping("/")
    public String index() {
        return is.index();
    }
	
}
