package com.cloud.examsystem.exam.controller;


import com.cloud.examsystem.exam.entity.Exam;
import com.cloud.examsystem.exam.service.ExamService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/instructor")
@AllArgsConstructor
public class InstructorController {

    @GetMapping
    public String createInstructorPage(Model model){

        return "/Instructor/InstructorHome";
    }

    @GetMapping("exam/create")
    public String createQuestionPage(Model model){
        model.addAttribute("question_number",3);
        model.addAttribute("option_number",4);
        return "/exam/create_exam";
    }
}
