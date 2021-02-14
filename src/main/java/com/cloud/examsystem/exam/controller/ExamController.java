package com.cloud.examsystem.exam.controller;


import com.cloud.examsystem.common.dto.DatatableRequest;
import com.cloud.examsystem.common.util.PaginationUtils;
import com.cloud.examsystem.exam.entity.Exam;
import com.cloud.examsystem.exam.service.ExamService;
import com.cloud.examsystem.grade.service.GradeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/exam")
@AllArgsConstructor
@Log4j2
public class ExamController {
    private final ExamService examService;
    GradeService gradeService;

    @GetMapping("/{id}") //exam getter
    public String getExamById(@PathVariable("id") Long id, Model model) {
        Optional<Exam> examOpt = examService.getExamById(id);
        if (!examOpt.isPresent()) {
            return "/error";
        }
        model.addAttribute("data", examOpt.get());
        return "/exam/apply";
    }

    @PostMapping("/{id}")
    public void applyExam(@ModelAttribute Exam model){
        log.info(model);
        examService.solve(model);
    }

    @GetMapping("/list")
    public String getExamListPage(Model model) {
        return "/exam/exam_list";
    }

    @PostMapping("/list")
    @ResponseBody
    public Map getListWithPagination(@ModelAttribute DatatableRequest request) {
        return PaginationUtils.createResultSet(examService.getActivesForInstructor(request), request);
    }


    @GetMapping("/create")
    public String createQuestionPage(Model model){
        model.addAttribute("question_number",3);
        model.addAttribute("option_number",4);
        return "/exam/create_exam";
    }


    @GetMapping("/home")
    public String startExam(Model model) {

        return "/exam/examPage";
    }

    @PostMapping("/exam/list")
    public Map getExamList(DatatableRequest request) {
        return PaginationUtils.createResultSet(examService.getAllActiveRecords(request), request);
    }

// student results
//    @GetMapping("exam/detail/{id}")
//    public String getResultList(@PathVariable("id") Long studentId, Model model) {
//        model.addAttribute("result", studentId);
//        return "/student/result/list";
//    }
//
//    @PostMapping("/student/result")
//
//    public Map postResultList(DatatableRequest request) {
//        return PaginationUtils.createResultSet(gradeService.getAllResultsbyStudentId(request), request);
//    }


}
