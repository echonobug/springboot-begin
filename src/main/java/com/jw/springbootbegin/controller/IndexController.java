package com.jw.springbootbegin.controller;

import com.github.pagehelper.PageInfo;
import com.jw.springbootbegin.dto.QuestionAndUserDTO;
import com.jw.springbootbegin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {

        PageInfo<QuestionAndUserDTO> pageInfo = questionService.getAll(page, pageSize);
        model.addAttribute("indexPageInfo", pageInfo);
        model.addAttribute("indexPageAction","/?page=");
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name="keyword",defaultValue = "") String keyword,
                         @RequestParam(name = "page", defaultValue = "1") Integer page,
                         @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                         Model model, HttpServletRequest request) {
        if(!keyword.equals("")){
            request.getSession().setAttribute("indexSearchKeyword",keyword);
        }else{
            keyword = (String)request.getSession().getAttribute("indexSearchKeyword");
        }
        PageInfo<QuestionAndUserDTO> pageInfo = questionService.findAll(keyword,page, pageSize);
        model.addAttribute("indexPageInfo", pageInfo);
        model.addAttribute("indexPageAction","/search?page=");
        return "index";
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }
}
