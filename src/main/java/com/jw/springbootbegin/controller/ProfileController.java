package com.jw.springbootbegin.controller;

import com.github.pagehelper.PageInfo;
import com.jw.springbootbegin.dto.QuestionAndUserDTO;
import com.jw.springbootbegin.entity.Question;
import com.jw.springbootbegin.entity.User;
import com.jw.springbootbegin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        model.addAttribute("action", action);
        User user = (User) request.getSession().getAttribute("user");
        PageInfo<QuestionAndUserDTO> pageInfo = null;
        String actionName = "";
        if (action.equals("myQuestion")) {
            actionName = "我的提问";
            pageInfo = questionService.findByCreatorId(user.getId(), page, pageSize);
        } else if (action.equals("")) {

        } else {
            pageInfo = new PageInfo<>();
        }
        model.addAttribute("actionName",actionName);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageAction","/profile/"+action+"?page=");
        return "profile";
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }
}
