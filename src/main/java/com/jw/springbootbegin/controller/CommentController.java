package com.jw.springbootbegin.controller;

import com.jw.springbootbegin.dto.CommentDTO;
import com.jw.springbootbegin.mapper.CommentMapper;
import com.jw.springbootbegin.model.Comment;
import com.jw.springbootbegin.model.User;
import com.jw.springbootbegin.result.Result;
import com.jw.springbootbegin.result.ResultEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    private CommentMapper commentMapper;

    @ResponseBody
    @PostMapping("/comment")
    public Result<Comment> comment(@RequestBody CommentDTO commentDTO,
                          HttpServletRequest request){
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO,comment);
        User user = (User) request.getSession().getAttribute("user");
       // comment.setCreatorId(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        //commentMapper.insertSelective(comment);
        return new Result(ResultEnum.SUCCESS,comment);
    }

    @Autowired
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }
}
