package com.jw.springbootbegin.dto;

import lombok.Data;

@Data
public class GithubUser {
    private Integer id;
    private String name;
    private String bio;
    private String avatarUrl;
}
