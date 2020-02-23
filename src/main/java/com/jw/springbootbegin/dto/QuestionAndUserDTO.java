package com.jw.springbootbegin.dto;

import com.jw.springbootbegin.entity.Question;
import com.jw.springbootbegin.entity.User;
import lombok.Data;

@Data
public class QuestionAndUserDTO {
    private Question question;
    private User user;
}
