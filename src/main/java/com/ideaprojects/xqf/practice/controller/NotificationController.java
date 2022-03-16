package com.ideaprojects.xqf.practice.controller;

import com.ideaprojects.xqf.practice.dto.NotificationDTO;
import com.ideaprojects.xqf.practice.enums.NotificationEnum;
import com.ideaprojects.xqf.practice.model.User;
import com.ideaprojects.xqf.practice.service.CommentService;
import com.ideaprojects.xqf.practice.service.NotificationService;
import com.ideaprojects.xqf.practice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String notification(@PathVariable(name = "id") Long id,
                           HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);
        if (NotificationEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        }else{
            return "redirect:/";
        }
    }
}
