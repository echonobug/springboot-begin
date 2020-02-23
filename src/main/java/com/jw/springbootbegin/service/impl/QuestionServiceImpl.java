package com.jw.springbootbegin.service.impl;

import com.jw.springbootbegin.dto.QuestionAndUserDTO;
import com.jw.springbootbegin.entity.Question;
import com.jw.springbootbegin.entity.User;
import com.jw.springbootbegin.mapper.QuestionMapper;
import com.jw.springbootbegin.mapper.UserMapper;
import com.jw.springbootbegin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private QuestionMapper questionMapper;
    private UserMapper userMapper;
    @Override
    public List<QuestionAndUserDTO> getAll() {
        List<QuestionAndUserDTO> list = new ArrayList<>();
        List<Question> questions = questionMapper.queryAll();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreatorId());
            QuestionAndUserDTO questionAndUserDTO = new QuestionAndUserDTO();
            questionAndUserDTO.setUser(user);
            questionAndUserDTO.setQuestion(question);
            list.add(questionAndUserDTO);
        }
        return list;
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
