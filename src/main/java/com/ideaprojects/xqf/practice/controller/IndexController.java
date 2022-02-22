package com.ideaprojects.xqf.practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //注解
public class IndexController {
    @GetMapping("/")
    public String index() { return "index"; }
}
