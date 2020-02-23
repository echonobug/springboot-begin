package com.jw.springbootbegin.controller;

import com.jw.springbootbegin.dto.QuestionAndUserDTO;
import com.jw.springbootbegin.entity.Question;
import com.jw.springbootbegin.entity.User;
import com.jw.springbootbegin.mapper.QuestionMapper;
import com.jw.springbootbegin.mapper.UserMapper;
import com.jw.springbootbegin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    private UserMapper userMapper;
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String value = cookie.getValue();
                User user = userMapper.findByToken(value);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        List<QuestionAndUserDTO> questions = questionService.getAll();
        model.addAttribute("questions",questions);
        return "index";
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }
}
