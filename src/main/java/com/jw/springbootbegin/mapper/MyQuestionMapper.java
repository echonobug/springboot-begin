package com.jw.springbootbegin.mapper;

import com.jw.springbootbegin.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MyQuestionMapper {

    @Update("update question set view_count=view_count+1 " +
            "where id=#{id}")
    void incView(Long id);
}
