package com.jw.springbootbegin.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private String userName;
    private Long questionId;
    private String questionTitle;
    private Long gmt_reply;
    private Integer status;
}
