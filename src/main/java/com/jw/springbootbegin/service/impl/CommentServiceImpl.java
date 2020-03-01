package com.jw.springbootbegin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jw.springbootbegin.dto.CommentAndUserDTO;
import com.jw.springbootbegin.dto.CommentDTO;
import com.jw.springbootbegin.mapper.CommentMapper;
import com.jw.springbootbegin.mapper.MyCommentMapper;
import com.jw.springbootbegin.mapper.MyQuestionMapper;
import com.jw.springbootbegin.mapper.UserMapper;
import com.jw.springbootbegin.model.Comment;
import com.jw.springbootbegin.model.CommentExample;
import com.jw.springbootbegin.model.User;
import com.jw.springbootbegin.service.CommentService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private UserMapper userMapper;
    private CommentMapper commentMapper;
    private MyQuestionMapper myQuestionMapper;
    private MyCommentMapper myCommentMapper;
    private ThymeleafViewResolver thymeleafViewResolver;

    @Override
    @Transactional
    public void insert(CommentDTO commentDTO, Long id) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setCreatorId(id);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        commentMapper.insertSelective(comment);
        if(comment.getType()==1){
            myQuestionMapper.incComment(comment.getParentId());
        }else{
            myCommentMapper.incReply(comment.getParentId());
        }
    }

    @Override
    public PageInfo<CommentAndUserDTO> queryByTypeAndParentId(int type, Long id, Integer page, Integer pageSize) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andTypeEqualTo(type).andParentIdEqualTo(id);
        PageHelper.startPage(page, pageSize);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        PageInfo<Comment> commentPageInfo = new PageInfo<>(comments, 5);
        List<CommentAndUserDTO> list = getCommentAndUserDTO(comments);
        PageInfo<CommentAndUserDTO> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(commentPageInfo, pageInfo);
        pageInfo.setList(list);
        return pageInfo;
    }

    @NotNull
    private List<CommentAndUserDTO> getCommentAndUserDTO(List<Comment> comments) {
        List<CommentAndUserDTO> list = new ArrayList<>();
        for (Comment comment : comments) {
            User user = userMapper.selectByPrimaryKey(comment.getCreatorId());
            CommentAndUserDTO commentAndUserDTO = new CommentAndUserDTO();
            commentAndUserDTO.setComment(comment);
            commentAndUserDTO.setUser(user);
            list.add(commentAndUserDTO);
        }
        return list;
    }

    @Override
    public Long incLike(Long id) {
        myCommentMapper.incLike(id);
        Comment comment = commentMapper.selectByPrimaryKey(id);
        return comment.getLikeCount();
    }

    @Override
    public List<CommentAndUserDTO> getSecondaryCommentAndUserDAO(Long id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andTypeEqualTo(2).andParentIdEqualTo(id);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        return getCommentAndUserDTO(comments);
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

    @Autowired
    public void setMyCommentMapper(MyCommentMapper myCommentMapper) {
        this.myCommentMapper = myCommentMapper;
    }

    @Autowired
    public void setThymeleafViewResolver(ThymeleafViewResolver thymeleafViewResolver) {
        this.thymeleafViewResolver = thymeleafViewResolver;
    }
}
