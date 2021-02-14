package com.cloud.examsystem.exam.service;

import com.cloud.examsystem.common.dto.DatatableRequest;
import com.cloud.examsystem.exam.entity.Exam;
import com.cloud.examsystem.exam.repository.ExamRepository;
import com.cloud.examsystem.user.entity.User;
import com.cloud.examsystem.user.service.UserAuthService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ExamService {
    private final ExamRepository examRepository;
    private final UserAuthService userAuthService;

    public Optional<Exam> getExamById(Long id) {
        return examRepository.getById(id);
    }

    public Exam save(Exam exam) {
        return examRepository.save(exam);
    }

    public Page<Exam> getAll(DatatableRequest request) {
        return examRepository.findActiveRecords(request.toPageRequest());
    }

    public Page<Exam> getAllForInstructor(DatatableRequest request) {
        User user = userAuthService.getCurrentUser();
        return examRepository.findActiveRecordsForInstructor(user.getDb_id(), request.toPageRequest());
    }
}