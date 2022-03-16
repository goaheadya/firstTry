package com.ideaprojects.xqf.practice.mapper;

import com.ideaprojects.xqf.practice.dto.QuestionQueryDTO;
import com.ideaprojects.xqf.practice.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);

    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}