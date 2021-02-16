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
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.rmi.UnexpectedException;
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

    public Exam updateExam(Exam exam) {
        return examRepository.save(exam);
    }

    public List<Exam> getAllRecordsOfStudent() {
        return examRepository.findAllActiveRecords();
    }

    public List<Exam> getAllPendingRecords() {
        return examRepository.findAllPendingRecords();
    }

    public Page<Exam> getAllPendingRecordsWithPagination(DatatableRequest request) {
        return examRepository.findAllPendingRecordsWithPagination(request.toPageRequest());
    }

    public Page<Exam> getAllActiveRecordsWithPagination(DatatableRequest request) {
        return examRepository.findAllActiveRecordsWithPagination(request.toPageRequest());
    }

    public Page<Exam> getAllCompletedRecordsWithPagination(DatatableRequest request) {
        return examRepository.findAllCompletedRecordsWithPagination(request.toPageRequest());
    }

    public Page<Exam> getAllRecordsOfStudent(DatatableRequest request) {
        return examRepository.findRecords(request.toPageRequest());
    }

    public Page<Exam> findAllRecordsForInstructorWithPaging(DatatableRequest request) {
        User user = userAuthService.getCurrentUser();
        return examRepository.findAllRecordsForInstructor(user.getDb_id(), request.toPageRequest());
    }

    public Page<Exam> getActivesForInstructor(DatatableRequest request) {
        User user = userAuthService.getCurrentUser();
        Long instructorId = userAuthService.getCurrentUser().getDb_id();
        return examRepository.findActiveRecordsForInstructorWithPaging(instructorId, request.toPageRequest());
    }

    public Page<Exam> getCompletedForInstructor(DatatableRequest request) {
        User user = userAuthService.getCurrentUser();
        Long instructorId = userAuthService.getCurrentUser().getDb_id();
        return examRepository.findCompletedRecordsForInstructorWithPaging(instructorId, request.toPageRequest());
    }

    public Page<Exam> getPendingsForInstructor(DatatableRequest request) {
        User user = userAuthService.getCurrentUser();
        return examRepository.findPendingRecordsForInstructorWithPaging(user.getDb_id(), request.toPageRequest());
    }

    public Page<Exam> getPassiveForInstructor(DatatableRequest request) {
        User user = userAuthService.getCurrentUser();
        return examRepository.findPassiveRecordsForInstructorWithPaging(user.getDb_id(), request.toPageRequest());
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
                totalScore += q.getScore();
            }

            answerDTO.setQuestionNumber(q.getNumber());
            answerDTO.setAnswers(questionSolve.getOptionList());
            grade.getSolves().add(answerDTO);
        }
        grade.setScore(totalScore);
        gradeService.save(grade);
    }

    public void activateExam(Long examId) throws UnexpectedException {
        Optional<Exam> examOptional = examRepository.getById(examId);
        if (examOptional.isPresent()){
            Exam exam = examOptional.get();
            exam.setStatus(StatusType.ACTIVE);
            examRepository.save(exam);
        }
        else {
            throw new UnexpectedException("Id not Found.");
        }
    }
}
