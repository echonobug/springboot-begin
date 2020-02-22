package com.jw.springbootbegin.mapper;

import com.jw.springbootbegin.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface QuestionMapper {
    @Insert("insert into " +
            "question(title,description,tag,gmt_create,gmt_modified,creator_id) " +
            "values (#{title},#{description},#{tag},#{gmtCreate},#{gmtModified},#{creatorId})")
    void insert(Question question);
}
