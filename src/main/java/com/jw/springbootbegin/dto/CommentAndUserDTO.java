package com.jw.springbootbegin.dto;

import com.jw.springbootbegin.model.Comment;
import com.jw.springbootbegin.model.User;
import lombok.Data;

@Data
public class CommentAndUserDTO {
    private Comment comment;
    private User user;
}
