package com.jw.springbootbegin.controller;

import com.jw.springbootbegin.dto.CommentDTO;
import com.jw.springbootbegin.model.Comment;
import com.jw.springbootbegin.model.User;
import com.jw.springbootbegin.result.Result;
import com.jw.springbootbegin.result.ResultEnum;
import com.jw.springbootbegin.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Result<Comment> comment(@RequestBody CommentDTO commentDTO,
                          HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        commentService.insert(commentDTO,user.getId());
        return new Result<>(ResultEnum.SUCCESS);
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
}
