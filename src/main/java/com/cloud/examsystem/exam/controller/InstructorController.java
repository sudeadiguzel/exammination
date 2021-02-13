package com.cloud.examsystem.exam.controller;


import com.cloud.examsystem.common.dto.DatatableRequest;
import com.cloud.examsystem.common.util.PaginationUtils;
import com.cloud.examsystem.exam.entity.Exam;
import com.cloud.examsystem.exam.model.Question;
import com.cloud.examsystem.exam.model.countPage;
import com.cloud.examsystem.exam.service.ExamService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@Log4j2
@Controller
@RequestMapping("/instructor")
@AllArgsConstructor
public class InstructorController {

    ExamService examService;

    @GetMapping
    public String createInstructorPage(Model model) {

        return "Instructor/InstructorHome";
    }

    @GetMapping("exam/create")
    public String createQuestionPage(Model model,@ModelAttribute("counts") countPage counts){
        log.info(counts);
//        this.countPage=counts;
        Exam exam = new Exam();
        exam.setQuestionCount(counts.getQuestionCount());
        exam.setOptionCount(counts.getOptionCount());
        exam = examService.save(exam);
        model.addAttribute("data", exam);
//
//        model.addAttribute("data",counts.getQuestionCount());
//        model.addAttribute("optionCount",counts.getOptionCount());
        return "exam/create_exam";
    }
    @GetMapping("exam/createPage")
    public String createExamPage() {

        return "/Instructor/createExamPage";
    }

    @GetMapping("exam/detail/{id}")
    public String examEdit(@PathVariable("id") Long examId, Model model) {
        model.addAttribute("data", examService.getExamById(examId).get());
        return "/Instructor/edit";
    }

    @PostMapping("exam/list")
    public Map getExamList(@PathVariable("id") Long instructorId, DatatableRequest request) {
        return PaginationUtils.createResultSet(examService.getAllForInstructor(request), request);
    }
    @PostMapping("a")
    public String postDeneme(Model model)
    {
//        Exam exam=new Exam();
//        for(int i=0;i<this.countPage.getQuestionCount();i++){
//            Question question=new Question();
//            for(int j=0;j<this.countPage.getOptionCount();j++){
//                String optionLabel= (String) model.getAttribute("option-".concat(String.valueOf(j)));
//                Boolean optionAnswerCheck= (Boolean) model.getAttribute("optionAnswerCheck-".concat(String.valueOf(j)));
//                Option option=new Option();
//                option.setContent(optionLabel);
//                option.setCorrect(optionAnswerCheck);
//                question.getOptionList().add(option);
//            }
//            exam.getQuestion().add(question);
//        }

//        log.info(exam);
        return "/exam/create_exam";
    }

}
