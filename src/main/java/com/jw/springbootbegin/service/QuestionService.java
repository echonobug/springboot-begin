package com.jw.springbootbegin.service;

import com.github.pagehelper.PageInfo;
import com.jw.springbootbegin.dto.QuestionAndUserDTO;

public interface QuestionService {
    PageInfo<QuestionAndUserDTO> getAll(Integer page, Integer pageSize);

    PageInfo<QuestionAndUserDTO> findAll(String keyword, Integer page, Integer pageSize);

    PageInfo<QuestionAndUserDTO> findByCreatorId(Long id, Integer page, Integer pageSize);

    QuestionAndUserDTO findById(Long id);

    void createOrUpdate(Long id, String title, String desc, String tag, Long userId);

    void incView(Long id);
}
