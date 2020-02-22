package com.jw.springbootbegin.controller;

import com.jw.springbootbegin.entity.Question;
import com.jw.springbootbegin.entity.User;
import com.jw.springbootbegin.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("desc") String desc,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
    ){
        Question question = new Question();
        Object userObject = request.getSession().getAttribute("user");
        if(userObject==null){
            model.addAttribute("error","用户未登录！");
            return "publish";
        }else{
            User user = (User) userObject;
            question.setCreatorId(user.getId());
        }
        question.setTitle(title);
        question.setDescription(desc);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.insert(question);
        return "redirect:/";
    }

    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }
}
