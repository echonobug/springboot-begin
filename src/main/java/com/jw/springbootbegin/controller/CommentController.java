package com.jw.springbootbegin.controller;

import com.github.pagehelper.PageInfo;
import com.jw.springbootbegin.dto.CommentAndUserDTO;
import com.jw.springbootbegin.dto.CommentDTO;
import com.jw.springbootbegin.dto.QuestionAndUserDTO;
import com.jw.springbootbegin.enums.CommentTypeEnum;
import com.jw.springbootbegin.model.Comment;
import com.jw.springbootbegin.model.Question;
import com.jw.springbootbegin.model.User;
import com.jw.springbootbegin.result.Result;
import com.jw.springbootbegin.result.ResultEnum;
import com.jw.springbootbegin.service.CommentService;
import com.jw.springbootbegin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    private CommentService commentService;
    private QuestionService questionService;

    @ResponseBody
    @PostMapping("/comment")
    public Result<Comment> comment(@RequestBody CommentDTO commentDTO,
                                   HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        commentService.insert(commentDTO, user.getId());
        return new Result<>(ResultEnum.SUCCESS);
    }

    @PostMapping("/comment/comment")
    public String comment(@RequestParam("id") Long id, Model model) {
        QuestionAndUserDTO questionAndUserDTO = questionService.findById(id);
        PageInfo<CommentAndUserDTO> pageInfo = commentService.queryByTypeAndParentId(1, id, 1, 5);
        model.addAttribute("questionAndUser", questionAndUserDTO);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pageAction", "/question/" + id + "?page=");
        return "list :: comment-list";
    }

    @PostMapping("/comment/secondary")
    public String secondaryComment(@RequestParam("id") Long id, Model model) {
        List<CommentAndUserDTO> secondaryCommentAndUserDAO = commentService.getSecondaryCommentAndUserDAO(id);
        model.addAttribute("id", id);
        model.addAttribute("list", secondaryCommentAndUserDAO);
        return "secondary-comment :: secondary-list";
    }


    @ResponseBody
    @PostMapping("/like")
    public Result<Long> like(@RequestParam("id") Long id) {
        return new Result<>(ResultEnum.SUCCESS, commentService.incLike(id));
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
