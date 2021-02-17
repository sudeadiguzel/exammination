package com.cloud.examsystem.exam.controller;


import com.cloud.examsystem.common.dto.DatatableRequest;
import com.cloud.examsystem.common.util.PaginationUtils;
import com.cloud.examsystem.exam.entity.Exam;
import com.cloud.examsystem.exam.model.countPage;
import com.cloud.examsystem.exam.service.ExamService;
import com.cloud.examsystem.grade.service.GradeService;
import com.cloud.examsystem.user.service.UserAuthService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.rmi.UnexpectedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@Log4j2
@Controller
@RequestMapping("/instructor")
@AllArgsConstructor
public class InstructorController {

    UserAuthService userAuthService;
    ExamService examService;
    GradeService gradeService;

    @GetMapping
    public String createInstructorPage(Model model) {
        return "instructor/home";
    }

    @GetMapping("exam/actives")
    public String getActiveExamsListPage(Model model) {
        return "instructor/active_list";
    }

    @GetMapping("exam/passives")
    public String getPassiveExamsListPage(Model model) {
        return "instructor/passive_list";
    }

    @GetMapping("exam/pendings")
    public String getPendingExamsListPage(Model model) {

        return "instructor/pending_list";
    }

    @GetMapping("exam/completed")
    public String getCompletedExamsListPage(Model model) {

        return "instructor/completed_list";
    }

    @PostMapping("exam/list")
    @ResponseBody
    public Map getAllExamsList(DatatableRequest request) {
        return PaginationUtils.createResultSet(examService.findAllRecordsForInstructorWithPaging(request), request);
    }

    @PostMapping("exam/actives")
    @ResponseBody
    public Map getActiveExamsList(DatatableRequest request) {
        return PaginationUtils.createResultSet(examService.getActivesForInstructor(request), request);
    }
    @PostMapping("exam/completed")
    @ResponseBody
    public Map getCompletedExamsList(DatatableRequest request) {
        return PaginationUtils.createResultSet(examService.getCompletedForInstructor(request), request);
    }

    @PostMapping("exam/pendings")
    @ResponseBody
    public Map getPendingExamsList(DatatableRequest request) {
        return PaginationUtils.createResultSet(examService.getPendingsForInstructor(request), request);
    }

    @PostMapping("exam/passives")
    @ResponseBody
    public Map getPassiveExamsList(DatatableRequest request) {
        return PaginationUtils.createResultSet(examService.getPassiveForInstructor(request), request);
    }

    @GetMapping("exam/create")
    public String createQuestionPage(Model model, @ModelAttribute("counts") countPage counts) {
        Exam exam = new Exam(counts.getQuestionCount(), counts.getOptionCount());
        log.info(counts);
        try {
            Date dateStart = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(counts.getStartDate());
            exam.setStartDate(dateStart);
            Date dateEnd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(counts.getEndDate());
            exam.setEndDate(dateEnd);
            exam.setName(counts.getName());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        model.addAttribute("data", exam);
//
        log.info(exam);
//        model.addAttribute("data",counts.getQuestionCount());
//        model.addAttribute("optionCount",counts.getOptionCount());
        return "instructor/create_exam";
    }

    @GetMapping("exam/createPage")
    public String createExamPage() {

        return "instructor/get_exam_parameter";
    }

    @GetMapping("exam/detail/{id}")
    public String examEdit(@PathVariable("id") Long examId, Model model) {
        model.addAttribute("data", examService.getExamById(examId).get());
        return "instructor/edit";
    }

    @GetMapping("exam/resultDetail/{id}")
    public String examResultPage(@PathVariable("id") Long examId, Model model) {
        model.addAttribute("examId", examId);
        return "instructor/exam_application_list";
    }

    @PostMapping("/exam/results/{id:\\d+}")
    @ResponseBody
    public Map getGradeList(@PathVariable("id") Long examId, DatatableRequest request) {
        return PaginationUtils.createResultSet(gradeService.getAllbyExamIdAsPage(request, examId), request);
    }


    @PostMapping("save")
    public String postDeneme(@ModelAttribute("data") Exam model) {
        examService.save(model);
        log.info(model);
        return "redirect:/instructor";
    }

    @GetMapping("exam/activate/{examId}")
    public String activeExamById(@PathVariable("examId") Long examId) throws UnexpectedException {
        examService.activateExam(examId);
        return "redirect:/instructor/exam/actives";
    }

}
