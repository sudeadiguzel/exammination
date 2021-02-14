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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

//        model.addAttribute("type","all");
        return "Instructor/InstructorHome";
    }

    @GetMapping("exam/actives")
    public String getActiveExamsListPage(Model model) {

//        model.addAttribute("type","active");
        return "Instructor/InstructorHome";
    }
    @PostMapping("exam/list")
    @ResponseBody
    public Map getAllExamsList(DatatableRequest request) {
        return PaginationUtils.createResultSet(examService.getActivesForInstructor(request), request);
    }
    @PostMapping("exam/actives")
    @ResponseBody
    public Map getActiveExamsList(DatatableRequest request) {
        return PaginationUtils.createResultSet(examService.getActivesForInstructor(request), request);
    }

    @GetMapping("exam/create")
    public String createQuestionPage(Model model,@ModelAttribute("counts") countPage counts){
        Exam exam = new Exam(counts.getQuestionCount(),counts.getOptionCount());
        log.info(counts);
        try {
            Date dateStart=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(counts.getStartDate());
            exam.setStartDate(dateStart);
            Date dateEnd=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(counts.getEndDate());
            exam.setEndDate(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        model.addAttribute("data", exam);
//
        log.info(exam);
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
        return "Instructor/edit";
    }

    @GetMapping("exam/resultDetail/{id}")
    public String examResult(@PathVariable("id") Long examId,Model model){
        model.addAttribute("examId",examId);
        return "/Instructor/ResultsPage";
    }

    @PostMapping("/exam/results/{id:\\d+}")
    @ResponseBody
    public Map getGradeList(@PathVariable("id") Long examId, DatatableRequest request){
      return PaginationUtils.createResultSet(gradeService.getAllbyExamId(request,examId),request);
    }


    @PostMapping("save")
    public String postDeneme(@ModelAttribute("data")Exam model)
    {
        examService.save(model);
        log.info(model);
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
        return "redirect:/instructor/";
    }

}
