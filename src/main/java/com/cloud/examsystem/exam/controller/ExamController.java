package com.cloud.examsystem.exam.controller;


import com.cloud.examsystem.common.dto.DatatableRequest;
import com.cloud.examsystem.common.util.PaginationUtils;
import com.cloud.examsystem.exam.entity.Exam;
import com.cloud.examsystem.exam.service.ExamService;
import com.cloud.examsystem.grade.service.GradeService;
import com.cloud.examsystem.user.service.UserAuthService;
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
    private final UserAuthService authService;

    @GetMapping("/{id}") //exam getter
    public String getExamById(@PathVariable("id") Long id, Model model) {
        Optional<Exam> examOpt = examService.getExamById(id);
        if (!examOpt.isPresent()) {
            return "error";
        }
        model.addAttribute("data", ExamConverter.ExamModelToStudentDTO(examOpt.get()));
        return "exam/apply";
    }

    @PostMapping("/")
    public String applyExam(@ModelAttribute Exam model) {
        examService.solve(model);
        return "redirect:/common/homepage";
    }

    @GetMapping("/list/pending")
    public String getPendingExamListPage() {
        return "exam/pending_list";
    }

    @PostMapping("/list/pending")
    @ResponseBody
    public Map getPendingExamList(DatatableRequest request) {
        return PaginationUtils.createResultSet(examService.getAllPendingRecordsWithPagination(request), request);
    }

       @GetMapping("/list/actives")
    public String getActiveExamListPage() {
        return "exam/active_list";
    }

    @PostMapping("/list/actives")
    @ResponseBody
    public Map getActiveExamList(DatatableRequest request) {
        return PaginationUtils.createResultSet(examService.getAllActiveRecordsWithPagination(request), request);
    }

    @GetMapping("/list/completed")
    public String getCompletedExamListPage() {
        return "exam/completed_list";
    }

    @PostMapping("/list/completed")
    @ResponseBody
    public Map getCompletedExamList(DatatableRequest request) {
        return PaginationUtils.createResultSet(examService.getAllCompletedRecordsWithPagination(request), request);
    }

    @PostMapping("/list")
    @ResponseBody
    public Map getListWithPagination(@ModelAttribute DatatableRequest request) {
        return PaginationUtils.createResultSet(examService.getAllRecordsOfStudent(request), request);
    }


    @GetMapping("/create")
    public String createQuestionPage(Model model) {
        model.addAttribute("question_number", 3);
        model.addAttribute("option_number", 4);
        return "exam/create_exam";
    }


    @GetMapping("/home")
    public String startExam(Model model) {
        model.addAttribute("appliedExams", gradeService.getAllbyStudentId(authService.getCurrentUser().getDb_id()));
        return "exam/home";
    }

    @PostMapping("/exam/list")
    public Map getExamList(DatatableRequest request) {
        return PaginationUtils.createResultSet(examService.getAllRecordsOfStudent(request), request);
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
