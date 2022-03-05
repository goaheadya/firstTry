package com.ideaprojects.xqf.practice.controller;

import com.ideaprojects.xqf.practice.dto.CommentDTO;
import com.ideaprojects.xqf.practice.dto.ResultDTO;
import com.ideaprojects.xqf.practice.exception.CustomizeErrorCode;
import com.ideaprojects.xqf.practice.mapper.CommentMapper;
import com.ideaprojects.xqf.practice.model.Comment;
import com.ideaprojects.xqf.practice.model.User;
import com.ideaprojects.xqf.practice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(
            @RequestBody CommentDTO commentDTO,
            HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
