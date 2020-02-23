package com.jw.springbootbegin.service;

import com.jw.springbootbegin.dto.QuestionAndUserDTO;

import java.util.List;

public interface QuestionService {
    List<QuestionAndUserDTO> getAll();
}
