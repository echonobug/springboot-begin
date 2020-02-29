package com.jw.springbootbegin.service;

import com.github.pagehelper.PageInfo;
import com.jw.springbootbegin.dto.CommentAndUserDTO;
import com.jw.springbootbegin.dto.CommentDTO;

public interface CommentService {
    void insert(CommentDTO commentDTO, Long id);

    PageInfo<CommentAndUserDTO> queryByTypeAndParentId(int type, Long id, Integer page, Integer pageSize);

    Long incLike(Long id);

    String getSecondaryCommentHtml(Long id);
}
