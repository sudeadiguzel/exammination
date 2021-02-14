package com.cloud.examsystem.exam.service;

import com.cloud.examsystem.common.dto.DatatableRequest;
import com.cloud.examsystem.exam.common.StatusType;
import com.cloud.examsystem.exam.entity.Exam;
import com.cloud.examsystem.exam.model.Question;
import com.cloud.examsystem.exam.model.QuestionAnswerDTO;
import com.cloud.examsystem.exam.repository.ExamRepository;
import com.cloud.examsystem.grade.entity.Grade;
import com.cloud.examsystem.grade.service.GradeService;
import com.cloud.examsystem.user.entity.User;
import com.cloud.examsystem.user.service.UserAuthService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExamService {
    private final ExamRepository examRepository;
    private final UserAuthService userAuthService;
    private final GradeService gradeService;

    public Optional<Exam> getExamById(Long id) {
        return examRepository.getById(id);
    }

    public Exam save(Exam exam) {
        exam.setUser(userAuthService.getCurrentUser());
        exam.setCreationTimestamp(new Date());
        return examRepository.save(exam);
    }

    public Exam updateExam(Exam exam){
        return examRepository.save(exam);
    }

    public List<Exam> getAllActiveRecords(){
        return examRepository.findAllActiveRecords();
    }
    public List<Exam> getAllPendingRecords(){
        return examRepository.findAllPendingRecords();
    }

    public Page<Exam> getAllActiveRecords(DatatableRequest request) {
        return examRepository.findRecords(request.toPageRequest());
    }

    public Page<Exam> getAllForInstructor(DatatableRequest request) {
        User user = userAuthService.getCurrentUser();
        return examRepository.findAllRecordsForInstructor(user.getDb_id(), request.toPageRequest());
    }

    public Page<Exam> getActivesForInstructor(DatatableRequest request) {
        User user = userAuthService.getCurrentUser();
        return examRepository.findActiveRecordsForInstructorByStatus(user.getDb_id(), StatusType.ACTIVE, request.toPageRequest());
    }


    public void solve(Exam examSolving) {
        Exam mainExam = this.getExamById(examSolving.getId()).get();
        Grade grade = new Grade();
        grade.setExam(mainExam);
        double totalScore = 0;
        grade.setStudent(userAuthService.getCurrentUser());
        for (Question q : mainExam.getQuestion()) {
            QuestionAnswerDTO answerDTO = new QuestionAnswerDTO();
            Question questionSolve = examSolving.getQuestionByQuestionNumber(q.getNumber());

            if (!questionSolve.isEquals(q)) {
                answerDTO.setCorrect(false);
                answerDTO.setStudentScore(0);
            } else {
                answerDTO.setCorrect(true);
                answerDTO.setStudentScore(q.getScore());
                totalScore+=q.getScore();
            }

            answerDTO.setQuestionNumber(q.getNumber());
            answerDTO.setAnswers(questionSolve.getOptionList());
            grade.getSolves().add(answerDTO);
        }
        grade.setScore(totalScore);
        gradeService.save(grade);


    }
}
