package com.ideaprojects.xqf.practice.controller;

import com.ideaprojects.xqf.practice.dto.CommentDTO;
import com.ideaprojects.xqf.practice.dto.QuestionDTO;
import com.ideaprojects.xqf.practice.enums.CommentTypeEnum;
import com.ideaprojects.xqf.practice.service.CommentService;
import com.ideaprojects.xqf.practice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionCotroller {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
        // 获取评论列表
        List<CommentDTO> comments = commentService.listByParentId(id, CommentTypeEnum.QUESTION);

        // 累加阅读数
        questionService.incView(id);
        QuestionDTO questionDTO = questionService.getById(id);

        // 给model赋值
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        return "question";
    }
}
