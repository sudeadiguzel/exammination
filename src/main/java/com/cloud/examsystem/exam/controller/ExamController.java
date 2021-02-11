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
@RequestMapping("/exam")
@AllArgsConstructor
public class ExamController {
    private final ExamService examService;

    @GetMapping("/{id}")
    public String getExamById(@RequestParam("id") Long id, Model model){
        Optional<Exam> examOpt = examService.getExamById(id);
        if (!examOpt.isPresent()){
            return "/error";
        }
        model.addAttribute("data", examOpt.get());
        return "/exam/apply/".concat(id.toString());
    }



}
