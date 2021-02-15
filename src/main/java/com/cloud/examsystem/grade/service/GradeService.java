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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class GradeService {
    private final GradeRepository gradeRepository;
    private final UserAuthService userAuthService;

    public Grade getById(Long db_id) {
        return gradeRepository.getByDbId(db_id);
    }


    public Page<Grade> getAllbyExamIdAsPage(DatatableRequest request, Long examId) {
        return gradeRepository.findActiveRecords(examId, request.toPageRequest());
    }

    public List<Long> getAllbyStudentId(Long studentId) {
        List<Grade> grades = gradeRepository.getAllByStudentId(studentId);
        List<Long> appliedExams = new ArrayList<>();
        for (Grade grade:grades){
            appliedExams.add(grade.getExam().getId());
        }

        return appliedExams;
    }

    public Page<Grade> getAllResultsbyStudentId(DatatableRequest request, Long gradeId) {
        User user = userAuthService.getCurrentUser();
        return gradeRepository.findStudentGrades(user.getDb_id(), gradeId, request.toPageRequest());
    }

    public void save(Grade grade) {
        gradeRepository.save(grade);
        log.info("Sınav Sonucu Kaydedildi.");
    }

    public boolean isStudentShowResult(Optional<Grade> gradeOptional) {
        if (gradeOptional.isPresent() && gradeOptional.get().getExam().getEndDate().getTime()<new Date().getTime()) {
            return true;
        }
        return false;
    }

    public Grade getForStudentById(Long examId) {
        Optional<Grade> gradeOptional = this.gradeRepository.findStudentGradeByExamID(examId, userAuthService.getCurrentUser().getDb_id());
        if (isStudentShowResult(gradeOptional)) {
            return gradeOptional.get();
        } else return null;
    }
}
