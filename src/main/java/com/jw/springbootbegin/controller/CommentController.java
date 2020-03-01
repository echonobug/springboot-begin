package com.jw.springbootbegin.controller;

import com.jw.springbootbegin.dto.CommentAndUserDTO;
import com.jw.springbootbegin.dto.CommentDTO;
import com.jw.springbootbegin.model.Comment;
import com.jw.springbootbegin.model.User;
import com.jw.springbootbegin.result.Result;
import com.jw.springbootbegin.result.ResultEnum;
import com.jw.springbootbegin.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Result<Comment> comment(@RequestBody CommentDTO commentDTO,
                                   HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        commentService.insert(commentDTO, user.getId());
        return new Result<>(ResultEnum.SUCCESS);
    }

    @PostMapping("/comment/secondary")
    public String secondaryComment(@RequestParam("id") Long id, Model model) {
        List<CommentAndUserDTO> secondaryCommentAndUserDAO = commentService.getSecondaryCommentAndUserDAO(id);
        model.addAttribute("id",id);
        model.addAttribute("list",secondaryCommentAndUserDAO);
        return "secondary-comment :: secondary-list";
    }



    @ResponseBody
    @PostMapping("/like")
    public Result<Long> like(@RequestParam("id") Long id) {
        return new Result<>(ResultEnum.SUCCESS, commentService.incLike(id));
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
}
