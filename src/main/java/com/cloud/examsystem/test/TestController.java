package com.cloud.examsystem.test;

import com.cloud.examsystem.exam.entity.Exam;
import com.cloud.examsystem.exam.service.ExamService;
import com.cloud.examsystem.user.service.UserAuthService;
import com.cloud.examsystem.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {
    private final ExamService examService;
    private final UserService userService;
    private final UserAuthService userAuthService;

    @GetMapping("/getExamById/{id}")
    public String getExamById(@PathVariable("id") Long id, Model model) {
        Optional<Exam> exam = examService.getExamById(5L);
        if (exam.isPresent()) {
            model.addAttribute("data",exam.get());
            return "/instructor/edit";
        }

        return "instructor/Home";
    }
}
