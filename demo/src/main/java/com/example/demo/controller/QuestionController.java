package com.example.demo.controller;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.model.Question;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
        questionService.incViewCount(id);
        QuestionDTO questionDTO = questionService.findById(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
