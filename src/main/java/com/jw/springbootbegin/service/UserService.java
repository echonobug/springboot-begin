package com.jw.springbootbegin.service;

import com.jw.springbootbegin.dto.GithubUser;

public interface UserService {
    String createOrUpdate(GithubUser githubUser);
}
