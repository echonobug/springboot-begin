package com.jw.springbootbegin.mapper;

import com.jw.springbootbegin.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    @Insert("insert into " +
            "user (name,account_id,token,gmt_create,gmt_modified,avatar_url) " +
            "values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") int id);

    @Select("select * from user where account_id=#{id}")
    User findByAccountId(Long id);

    @Update("update user set name=#{name},token=#{token}," +
            "gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} " +
            "where id=#{id}")
    void update(User user);
}
