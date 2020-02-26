package com.jw.springbootbegin.service;

import com.github.pagehelper.PageInfo;
import com.jw.springbootbegin.dto.QuestionAndUserDTO;

public interface QuestionService {
    PageInfo<QuestionAndUserDTO> getAll(Integer page, Integer pageSize);

    PageInfo<QuestionAndUserDTO> findAll(String keyword, Integer page, Integer pageSize);

    PageInfo<QuestionAndUserDTO> findByCreatorId(Integer id, Integer page, Integer pageSize);

    QuestionAndUserDTO findById(Integer id);
}
