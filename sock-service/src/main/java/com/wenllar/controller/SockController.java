package com.wenllar.controller;


import api.SockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SockController {

    SockService sockService;

    @Autowired
    public void setSockService(SockService sockService) {
        this.sockService = sockService;
    }

    @RequestMapping("/deductSock")
    public String deductSock(@RequestParam(name = "deductNum") int deductNo){
        return this.sockService.deductSock(deductNo);
    }
}
