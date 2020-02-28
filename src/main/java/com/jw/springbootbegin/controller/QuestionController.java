package com.jw.springbootbegin.controller;

import com.github.pagehelper.PageInfo;
import com.jw.springbootbegin.dto.CommentAndUserDTO;
import com.jw.springbootbegin.dto.QuestionAndUserDTO;
import com.jw.springbootbegin.service.CommentService;
import com.jw.springbootbegin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionController {
    private QuestionService questionService;
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model,
                           @RequestParam(name = "page", defaultValue = "1") Integer page,
                           @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        QuestionAndUserDTO questionAndUserDTO = questionService.findById(id);
        questionService.incView(questionAndUserDTO.getQuestion().getId());
        PageInfo<CommentAndUserDTO> pageInfo = commentService.queryByTypeAndParentId(1, id,page,pageSize);
        model.addAttribute("questionAndUser", questionAndUserDTO);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageAction", "/question/"+id+"?page=");
        return "question";
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
}
