package com.jw.springbootbegin.controller;

import com.github.pagehelper.PageInfo;
import com.jw.springbootbegin.dto.CommentAndUserDTO;
import com.jw.springbootbegin.dto.QuestionAndUserDTO;
import com.jw.springbootbegin.model.Question;
import com.jw.springbootbegin.service.CommentService;
import com.jw.springbootbegin.service.NotificationService;
import com.jw.springbootbegin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuestionController {
    private QuestionService questionService;
    private CommentService commentService;
    private NotificationService notificationService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model,
                           @RequestParam(name = "readId", defaultValue = "-1") Long readId,
                           @RequestParam(name = "page", defaultValue = "1") Integer page,
                           @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        if (readId != -1) {
            notificationService.read(readId);
        }
        QuestionAndUserDTO questionAndUserDTO = questionService.findById(id);
        List<Question> relatedQuestions = questionService.findRelatedQuestion(id, questionAndUserDTO.getQuestion().getTag());
        questionService.incView(questionAndUserDTO.getQuestion().getId());
        PageInfo<CommentAndUserDTO> pageInfo = commentService.queryByTypeAndParentId(1, id, page, pageSize);
        model.addAttribute("questionAndUser", questionAndUserDTO);
        model.addAttribute("relatedQuestions", relatedQuestions);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageAction", "/question/" + id + "?page=");
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

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
