package com.jw.springbootbegin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jw.springbootbegin.dto.NotificationDTO;
import com.jw.springbootbegin.enums.NotificationEnum;
import com.jw.springbootbegin.enums.NotificationStatusEnum;
import com.jw.springbootbegin.mapper.CommentMapper;
import com.jw.springbootbegin.mapper.NotificationMapper;
import com.jw.springbootbegin.mapper.QuestionMapper;
import com.jw.springbootbegin.mapper.UserMapper;
import com.jw.springbootbegin.model.*;
import com.jw.springbootbegin.service.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private NotificationMapper notificationMapper;
    private UserMapper userMapper;
    private CommentMapper commentMapper;
    private QuestionMapper questionMapper;

    @Override
    public PageInfo<NotificationDTO> findReply(Long id, Integer page, Integer pageSize) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(id).andTypeEqualTo(NotificationEnum.QuestionReply.getType());
        notificationExample.setOrderByClause("gmt_create desc");
        PageHelper.startPage(page, pageSize);
        List<Notification> notifications = notificationMapper.selectByExample(notificationExample);
        PageInfo<Notification> notificationPageInfo = new PageInfo<>(notifications);
        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for (Notification notification : notificationPageInfo.getList()) {
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setId(notification.getId());
            notificationDTO.setGmt_reply(notification.getGmtCreate());
            notificationDTO.setStatus(notification.getStatus());
            User user = userMapper.selectByPrimaryKey(notification.getInitiator());
            notificationDTO.setUserName(user.getName());
            Comment comment = commentMapper.selectByPrimaryKey(notification.getContentId());
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            notificationDTO.setQuestionId(question.getId());
            notificationDTO.setQuestionTitle(question.getTitle());
            notificationDTOList.add(notificationDTO);
        }
        PageInfo<NotificationDTO> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(notificationPageInfo,pageInfo);
        pageInfo.setList(notificationDTOList);
        return pageInfo;
    }

    @Override
    public long getUnreadReplyCount(Long id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(id).andTypeEqualTo(NotificationEnum.QuestionReply.getType())
                .andStatusEqualTo(NotificationStatusEnum.Unread.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    @Override
    public void read(Long readId) {
        Notification notification = notificationMapper.selectByPrimaryKey(readId);
        notification.setStatus(NotificationStatusEnum.Read.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
    }


    @Autowired
    public void setNotificationMapper(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }
}
