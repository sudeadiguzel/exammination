package com.cloud.examsystem.exam.controller;


import com.cloud.examsystem.exam.entity.Exam;
import com.cloud.examsystem.exam.model.Question;
import com.cloud.examsystem.exam.service.ExamService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
@AllArgsConstructor
public class ExamConverter {

    public static Exam ExamModelToStudentDTO(Exam exam){
        Exam examDTO = new Exam();
        for (Question q: exam.getQuestion()){
            Question dto = q.convertDto();
            examDTO.getQuestion().add(dto);
        }

        examDTO.setId(exam.getId());
        examDTO.setName(exam.getName());
        return examDTO;
    }

}
