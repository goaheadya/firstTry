package com.ideaprojects.xqf.practice.service;

import com.ideaprojects.xqf.practice.enums.CommentTypeEnum;
import com.ideaprojects.xqf.practice.exception.CustomizeErrorCode;
import com.ideaprojects.xqf.practice.exception.CustomizeException;
import com.ideaprojects.xqf.practice.mapper.CommentMapper;
import com.ideaprojects.xqf.practice.mapper.QuestionExtMapper;
import com.ideaprojects.xqf.practice.mapper.QuestionMapper;
import com.ideaprojects.xqf.practice.model.Comment;
import com.ideaprojects.xqf.practice.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException((CustomizeErrorCode.TYPE_PARAM_WRONG));
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        } else {
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }
    }
}
