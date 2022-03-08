package com.ideaprojects.xqf.practice.mapper;

import com.ideaprojects.xqf.practice.model.Comment;
import com.ideaprojects.xqf.practice.model.Question;

public interface CommentExtMapper {
    int incCommentCount(Comment record);
}