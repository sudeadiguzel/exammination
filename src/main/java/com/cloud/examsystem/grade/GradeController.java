package com.cloud.examsystem.grade;

import com.cloud.examsystem.exam.service.ExamService;
import com.cloud.examsystem.grade.entity.Grade;
import com.cloud.examsystem.grade.service.GradeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/grade")
@AllArgsConstructor
public class GradeController {
    private GradeService gradeService;
    private ExamService examService;


    @GetMapping("/{id}")
    public String getExamGrade(@PathVariable("id")Long id, Model model){
        Grade grade = gradeService.getForStudentById(id);
        if (grade == null){
            //TODO exam not ended page.
            return "redirect:/home";
        }
        model.addAttribute("grade",grade);
        model.addAttribute("exam",examService.getExamById(id).get());
        return "/exam/grade";
    }
    @GetMapping("/{exam_id}/{student_id}")
    public String getExamGradeByStudentId(@PathVariable("exam_id")Long examId,@PathVariable("student_id")Long studentId, Model model){
        Grade grade = gradeService.getGradeByStudentAndExamId(examId,studentId);
        if (grade == null){
            //TODO exam not ended page.
            return "redirect:/home";
        }
        model.addAttribute("grade",grade);
        model.addAttribute("exam",examService.getExamById(examId).get());
        return "/exam/grade";
    }

}
