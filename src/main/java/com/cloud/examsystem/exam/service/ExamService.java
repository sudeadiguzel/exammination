package com.cloud.examsystem.exam.service;

import com.cloud.examsystem.common.dto.DatatableRequest;
import com.cloud.examsystem.exam.entity.Exam;
import com.cloud.examsystem.exam.repository.ExamRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ExamService {
    private final ExamRepository examRepository;

    public Optional<Exam> getExamById(Long id) {
        return examRepository.getById(id);
    }

    public Exam save(Exam exam) {
        return examRepository.save(exam);
    }

    public Page<Exam> getAll(DatatableRequest request) {
        return examRepository.findActiveRecords(request.toPageRequest());
    }

    public Page<Exam> getAllForInstructor(Long instructorId, DatatableRequest request) {
        return examRepository.findActiveRecordsForInstructor(instructorId, request.toPageRequest());
    }
}
