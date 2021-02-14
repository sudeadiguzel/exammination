package com.cloud.examsystem.grade.service;

import com.cloud.examsystem.common.dto.DatatableRequest;
import com.cloud.examsystem.grade.entity.Grade;
import com.cloud.examsystem.grade.repository.GradeRepository;
import com.cloud.examsystem.user.entity.User;
import com.cloud.examsystem.user.service.UserAuthService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class GradeService {
    private final GradeRepository gradeRepository;
    private final UserAuthService userAuthService;

    public Grade getById(Long db_id) {
        return gradeRepository.getByDbId(db_id);
    }


    public Page<Grade> getAllbyExamId(DatatableRequest request, Long examId) {
        return gradeRepository.findActiveRecords(examId,request.toPageRequest());
    }

    public Page<Grade> getAllResultsbyStudentId(DatatableRequest request,Long gradeId){
        User user = userAuthService.getCurrentUser();
        return gradeRepository.findStudentGrades(user.getDb_id(),gradeId, request.toPageRequest());
    }
    public void save(Grade grade){
        gradeRepository.save(grade);
        log.info("Sınav Sonucu Kaydedildi.");
    }
}
