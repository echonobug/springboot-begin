package com.jw.springbootbegin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jw.springbootbegin.dto.CommentAndUserDTO;
import com.jw.springbootbegin.dto.CommentDTO;
import com.jw.springbootbegin.mapper.CommentMapper;
import com.jw.springbootbegin.mapper.MyQuestionMapper;
import com.jw.springbootbegin.mapper.UserMapper;
import com.jw.springbootbegin.model.Comment;
import com.jw.springbootbegin.model.CommentExample;
import com.jw.springbootbegin.model.User;
import com.jw.springbootbegin.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private UserMapper userMapper;
    private CommentMapper commentMapper;
    private MyQuestionMapper myQuestionMapper;

    @Override
    @Transactional
    public void insert(CommentDTO commentDTO, Long id) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO,comment);
        comment.setCreatorId(id);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        commentMapper.insertSelective(comment);
        myQuestionMapper.incComment(comment.getParentId());
    }

    @Override
    public PageInfo<CommentAndUserDTO> queryByTypeAndParentId(int type, Long id, Integer page, Integer pageSize) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andTypeEqualTo(type).andParentIdEqualTo(id);
        PageHelper.startPage(page, pageSize);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        PageInfo<Comment> commentPageInfo = new PageInfo<>(comments,5);
        List<CommentAndUserDTO> list = new ArrayList<>();
        for (Comment comment : comments) {
            User user = userMapper.selectByPrimaryKey(comment.getCreatorId());
            CommentAndUserDTO commentAndUserDTO = new CommentAndUserDTO();
            commentAndUserDTO.setComment(comment);
            commentAndUserDTO.setUser(user);
            list.add(commentAndUserDTO);
        }
        PageInfo<CommentAndUserDTO> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(commentPageInfo,pageInfo);
        pageInfo.setList(list);
        return pageInfo;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Autowired
    public void setMyQuestionMapper(MyQuestionMapper myQuestionMapper) {
        this.myQuestionMapper = myQuestionMapper;
    }
}
