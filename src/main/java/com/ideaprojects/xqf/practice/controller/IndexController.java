package com.ideaprojects.xqf.practice.controller;

import com.ideaprojects.xqf.practice.dto.PaginationDTO;
import com.ideaprojects.xqf.practice.dto.QuestionDTO;
import com.ideaprojects.xqf.practice.mapper.QuestionMapper;
import com.ideaprojects.xqf.practice.mapper.UserMapper;
import com.ideaprojects.xqf.practice.model.Question;
import com.ideaprojects.xqf.practice.model.User;
import com.ideaprojects.xqf.practice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller //注解
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(
            HttpServletRequest request,
            Model model,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "2") Integer size) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        PaginationDTO pagination = questionService.list(page, size);
        model.addAttribute("paginations", pagination);
        return "index";
    }
}
