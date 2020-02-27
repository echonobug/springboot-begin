package com.jw.springbootbegin.advice;

import com.jw.springbootbegin.exception.CustomException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    String handle(CustomException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

}
