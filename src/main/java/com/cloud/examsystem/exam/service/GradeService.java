package com.cloud.examsystem.exam.service;

import com.cloud.examsystem.common.dto.DatatableRequest;
import com.cloud.examsystem.exam.repository.GradeRepository;
import com.cloud.examsystem.grade.entity.Grade;
import com.cloud.examsystem.user.entity.User;
import com.cloud.examsystem.user.service.UserAuthService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final UserAuthService userAuthService;

    public Grade getById(Long db_id) {
        return gradeRepository.getByDbId(db_id);
    }
    public Grade save(Grade grade) {
        return gradeRepository.save(grade);
    }


    public Page<Grade> getAllbyExamId(DatatableRequest request,Long examId) {
        return gradeRepository.findActiveRecords(examId,request.toPageRequest());
    }

    public Page<Grade> getAllResultsbyStudentId(DatatableRequest request,Long gradeId){
        User user = userAuthService.getCurrentUser();
        return gradeRepository.findStudentGrades(user.getDb_id(),gradeId, request.toPageRequest());
    }
}
