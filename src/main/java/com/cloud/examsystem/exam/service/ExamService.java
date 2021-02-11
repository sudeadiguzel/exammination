package com.cloud.examsystem.exam.service;

import com.cloud.examsystem.exam.entity.Exam;
import com.cloud.examsystem.exam.repository.ExamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ExamService {
    private final ExamRepository examRepository;

    public Optional<Exam> getExamById(Long id){
        return examRepository.getById(id);
    }

    public void save(Exam exam){
        examRepository.save(exam);
    }
}
