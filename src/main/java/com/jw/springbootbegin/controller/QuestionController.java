package com.jw.springbootbegin.controller;

import com.jw.springbootbegin.dto.QuestionAndUserDTO;
import com.jw.springbootbegin.mapper.QuestionMapper;
import com.jw.springbootbegin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Integer id, Model model){
        QuestionAndUserDTO questionAndUserDTO = questionService.findById(id);
        model.addAttribute("questionAndUser",questionAndUserDTO);
        return "question";
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }
}
