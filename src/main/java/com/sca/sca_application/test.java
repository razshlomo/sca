package com.sca.sca_application;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class test {

    @PostConstruct
    private void init(){
        System.out.println("test post cont");
    }
}
