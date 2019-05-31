package com.greenleaves.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public class IndexService {

    public String index() {
        return "Hello World!";
    }

}
