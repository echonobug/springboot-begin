package com.jw.springbootbegin.service.impl;

import com.jw.springbootbegin.dto.GithubUser;
import com.jw.springbootbegin.entity.User;
import com.jw.springbootbegin.mapper.UserMapper;
import com.jw.springbootbegin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    @Override
    public String createOrUpdate(GithubUser githubUser) {
        User user =userMapper.findByAccountId(githubUser.getId());
        String token = UUID.randomUUID().toString();
        if(user == null){
            user = new User();
            updateUser(githubUser, user, token);
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            userMapper.insert(user);
        }else {
            updateUser(githubUser,user,token);
            userMapper.update(user);
        }
        return user.getToken();
    }

    private void updateUser(GithubUser githubUser, User user, String token) {
        user.setName(githubUser.getName());
        user.setToken(token);
        user.setGmtModified(System.currentTimeMillis());
        user.setAvatarUrl(githubUser.getAvatarUrl());
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
