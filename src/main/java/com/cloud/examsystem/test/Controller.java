package com.cloud.examsystem.test;

import com.cloud.examsystem.exam.entity.Exam;
import com.cloud.examsystem.exam.model.Option;
import com.cloud.examsystem.exam.model.Question;
import com.cloud.examsystem.exam.service.ExamService;
import com.cloud.examsystem.user.service.UserAuthService;
import com.cloud.examsystem.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class Controller {
    private final ExamService examService;
    private final UserService userService;
    private final UserAuthService userAuthService;

    @GetMapping("/getExamById/{id}")
    public Exam getExamById(@PathVariable("id") Long id) {

        userAuthService.getCurrentUser();
        Option o1 = new Option();
        o1.setContent("şık1");
        o1.setCorrect(false);
        Option o = new Option();
        o.setContent("şık2");
        o.setCorrect(true);

        Question question = new Question();
        Question question2 = new Question();
        question.getOptionList().add(o);
        question.getOptionList().add(o1);

        question.setDescription("Bu bir sorudur.");
        question2.setDescription("Bu bir sorudur.");

        question.setNumber(1);
        question.setNumber(2);

        question2.getOptionList().add(o);
        question2.getOptionList().add(o1);
        Exam exam = new Exam();
        exam.setId(5L);
        exam.setDescription("deneme");
        exam.setName("sınavDeneme");
        exam.setUser(userService.getByUsername("username"));
        exam.getQuestion().add(question);
        exam.getQuestion().add(question2);

        examService.save(exam);



        exam.getQuestion().get(0);
        return exam;

    }
}
