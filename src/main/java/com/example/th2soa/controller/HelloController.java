package com.example.th2soa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** API "Hello World" cua bai thuc hanh 1, nay duoc bao ve boi AuthFilter. */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}