package com.genesisairport.reservation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Slf4j
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        log.info("Test success");
        return "Hello, World!";
    }
}
