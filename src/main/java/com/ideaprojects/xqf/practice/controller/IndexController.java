package com.ideaprojects.xqf.practice.controller;

import com.ideaprojects.xqf.practice.dto.PaginationDTO;
import com.ideaprojects.xqf.practice.dto.QuestionDTO;
import com.ideaprojects.xqf.practice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller //注解
public class IndexController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "search", required = false) String search) {
        PaginationDTO<QuestionDTO> pagination = questionService.list(search, page, size);
        model.addAttribute("paginations", pagination);
        model.addAttribute("search", search);
        return "index";
    }
}
