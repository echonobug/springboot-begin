package com.jw.springbootbegin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jw.springbootbegin.dto.QuestionAndUserDTO;
import com.jw.springbootbegin.entity.Question;
import com.jw.springbootbegin.entity.User;
import com.jw.springbootbegin.mapper.QuestionMapper;
import com.jw.springbootbegin.mapper.UserMapper;
import com.jw.springbootbegin.service.QuestionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private QuestionMapper questionMapper;
    private UserMapper userMapper;

    @Override
    public PageInfo<QuestionAndUserDTO> getAll(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Question> questions = questionMapper.queryAll();
        return getQuestionAndUserDTOPageInfo(questions);
    }

    @Override
    public PageInfo<QuestionAndUserDTO> findAll(String keyword, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Question> questions = questionMapper.findAll(keyword);
        return getQuestionAndUserDTOPageInfo(questions);
    }

    @Override
    public PageInfo<QuestionAndUserDTO> findByCreatorId(Integer id, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Question> questions = questionMapper.findByCreatorId(id);
        return getQuestionAndUserDTOPageInfo(questions);
    }

    @Override
    public QuestionAndUserDTO findById(Integer id) {
        Question question = questionMapper.findById(id);
        if (question == null) {
            return null;
        }
        return getQuestionAndUserDTO(question);
    }

    @NotNull
    private QuestionAndUserDTO getQuestionAndUserDTO(Question question) {
        User user = userMapper.findById(question.getCreatorId());
        QuestionAndUserDTO questionAndUserDTO = new QuestionAndUserDTO();
        questionAndUserDTO.setUser(user);
        questionAndUserDTO.setQuestion(question);
        return questionAndUserDTO;
    }

    @NotNull
    private PageInfo<QuestionAndUserDTO> getQuestionAndUserDTOPageInfo(List<Question> questions) {
        List<QuestionAndUserDTO> list = new ArrayList<>();
        PageInfo<Question> questionPageInfo = new PageInfo<>(questions, 5);
        for (Question question : questionPageInfo.getList()) {
            QuestionAndUserDTO questionAndUserDTO = getQuestionAndUserDTO(question);
            list.add(questionAndUserDTO);
        }
        PageInfo<QuestionAndUserDTO> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(questionPageInfo, pageInfo);
        pageInfo.setList(list);
        return pageInfo;
    }


    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
