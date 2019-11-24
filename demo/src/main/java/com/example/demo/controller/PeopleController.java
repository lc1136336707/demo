package com.example.demo.controller;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.model.User;
import com.example.demo.service.PeopleService;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PeopleController {
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/people/{userId}")
    public String people(@PathVariable Integer userId,
                         Model model){
        User user = peopleService.getUserById(userId);
        model.addAttribute("user",user);
        return "people";
    }
    @GetMapping("/people/{userId}/questions")
    public String peopleQuestions(@PathVariable Integer userId, Model model,
                                  @RequestParam(name="page",defaultValue = "1") Integer page,
                                  @RequestParam(name="size",defaultValue = "5") Integer size){
        model.addAttribute("active","questions");
        User user = peopleService.getUserById(userId);
        model.addAttribute("user",user);
        PaginationDTO paginationDTO = questionService.getPaginationDTOById(userId,page,size);
        model.addAttribute("pagination",paginationDTO);
        return "people";
    }

}
