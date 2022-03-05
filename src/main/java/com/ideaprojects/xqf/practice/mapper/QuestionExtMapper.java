package com.ideaprojects.xqf.practice.mapper;

import com.ideaprojects.xqf.practice.model.Question;
import com.ideaprojects.xqf.practice.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
}