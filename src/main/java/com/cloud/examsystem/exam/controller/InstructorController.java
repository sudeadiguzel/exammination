package com.cloud.examsystem.exam.controller;


import com.cloud.examsystem.exam.entity.Exam;
import com.cloud.examsystem.exam.model.countPage;
import com.cloud.examsystem.exam.service.ExamService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@Log4j2
@Controller
@RequestMapping("/instructor")
@AllArgsConstructor
public class InstructorController {

    @GetMapping
    public String createInstructorPage(Model model){

        return "Instructor/InstructorHome";
    }

    @GetMapping("exam/create")
    public String createQuestionPage(Model model,@ModelAttribute("counts") countPage counts){
        log.info(counts);
        model.addAttribute("questionCount",counts.getQuestionCount());
        model.addAttribute("optionCount",counts.getOptionCount());
        return "exam/create_exam";
    }
    @GetMapping("exam/createPage")
    public String createExamPage(Model model){
        return "/Instructor/createExamPage";
    }
}
