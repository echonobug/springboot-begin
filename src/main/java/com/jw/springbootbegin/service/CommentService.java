package com.jw.springbootbegin.service;

import com.github.pagehelper.PageInfo;
import com.jw.springbootbegin.dto.CommentAndUserDTO;
import com.jw.springbootbegin.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    void insert(CommentDTO commentDTO, Long id);

    PageInfo<CommentAndUserDTO> queryByTypeAndParentId(int type, Long id, Integer page, Integer pageSize);

    Long incLike(Long id);

    List<CommentAndUserDTO> getSecondaryCommentAndUserDAO(Long id);
}
