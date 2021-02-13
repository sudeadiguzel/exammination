package com.cloud.examsystem.exam.controller;


import com.cloud.examsystem.common.dto.DatatableRequest;
import com.cloud.examsystem.common.util.PaginationUtils;
import com.cloud.examsystem.exam.entity.Exam;
import com.cloud.examsystem.exam.service.ExamService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/exam")
@AllArgsConstructor
public class ExamController {
    private final ExamService examService;

    @GetMapping("/{id}") //exam getter
    public String getExamById(@PathVariable("id") Long id, Model model) {
        Optional<Exam> examOpt = examService.getExamById(id);
        if (!examOpt.isPresent()) {
            return "/error";
        }
        model.addAttribute("data", examOpt.get());
        return "/exam/apply";
    }

    @GetMapping("/list")
    public String getExamListPage(Model model) {
        return "/exam/exam_list";
    }

    @PostMapping("/list")
    @ResponseBody
    public Map getListWithPagination(@ModelAttribute DatatableRequest request) {
        return PaginationUtils.createResultSet(examService.getAll(request), request);
    }


    @GetMapping("/create")
    public String createQuestionPage(Model model){
        model.addAttribute("question_number",3);
        model.addAttribute("option_number",4);
        return "exam/create_exam";
    }



}
