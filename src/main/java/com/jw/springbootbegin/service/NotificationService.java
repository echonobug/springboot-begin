package com.jw.springbootbegin.service;


import com.github.pagehelper.PageInfo;
import com.jw.springbootbegin.dto.NotificationDTO;

public interface NotificationService {
    PageInfo<NotificationDTO> findReply(Long id, Integer page, Integer pageSize) ;

    long getUnreadReplyCount(Long id);

    void read(Long readId);
}
