package com.jw.springbootbegin.dto;

import com.jw.springbootbegin.model.Question;
import com.jw.springbootbegin.model.User;
import lombok.Data;

@Data
public class QuestionAndUserDTO {
    private Question question;
    private User user;
}
