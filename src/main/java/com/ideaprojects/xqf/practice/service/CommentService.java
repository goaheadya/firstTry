package com.ideaprojects.xqf.practice.service;

import com.ideaprojects.xqf.practice.dto.CommentDTO;
import com.ideaprojects.xqf.practice.enums.CommentTypeEnum;
import com.ideaprojects.xqf.practice.enums.NotificationEnum;
import com.ideaprojects.xqf.practice.enums.NotificationStatusEnum;
import com.ideaprojects.xqf.practice.exception.CustomizeErrorCode;
import com.ideaprojects.xqf.practice.exception.CustomizeException;
import com.ideaprojects.xqf.practice.mapper.*;
import com.ideaprojects.xqf.practice.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional  //事务注解
    public void insert(Comment comment, User commentator) {
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
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            dbComment.setCommentCount(1);
            commentExtMapper.incCommentCount(dbComment);
            //创建通知
            if(dbComment.getCommentator() != commentator.getId() && question.getCreator() != commentator.getId()){
                createNotify(comment, dbComment.getParentId(), dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationEnum.REPLY_COMMENT);
            }
        } else {
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
            //创建通知
            if(question.getCreator() != commentator.getId()){
                createNotify(comment, question.getId(), question.getCreator(), commentator.getName(), question.getTitle(), NotificationEnum.REPLY_QUESTION);
            }
        }
    }

    private void createNotify(Comment comment, Long parentId, Long receiver, String notifierName, String outerTitle, NotificationEnum notificationEnum) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationEnum.getType());
        notification.setOuterId(parentId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> listByParentId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        // 获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList();
        userIds.addAll(commentators);

        // 获取评论人并转为 Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        // 转换 comment 为 commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
