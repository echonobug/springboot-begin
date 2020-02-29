package com.jw.springbootbegin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MyQuestionMapper {

    @Update("update question set view_count=view_count+1 " +
            "where id=#{id}")
    void incView(Long id);

    @Update("update question set comment_count=comment_count+1 " +
            "where id=#{id}")
    void incComment(Long id);
}
