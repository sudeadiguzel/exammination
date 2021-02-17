package com.cloud.examsystem.job.bean;

import com.cloud.examsystem.exam.common.StatusType;
import com.cloud.examsystem.exam.entity.Exam;
import com.cloud.examsystem.exam.service.ExamService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

@Log4j2
@Configuration
@EnableScheduling
@AllArgsConstructor
public class ExamDeadlineListener {
    private final ExamService examService;

    private void endExam() {
        log.info("EXAMS ENDING JOB ACTIVED");
        List<Exam> examList = examService.getAllRecordsOfStudent();
        for (Exam exam : examList) {
            if (exam.getEndDate().getTime()<new Date().getTime()) {
                log.info("EXAM: " + exam.getId() + ", ENDED AT: " + new Date());
                exam.setStatus(StatusType.ENDED);
                examService.updateExam(exam);
            }
        }
    }

    private void startExam() {
        log.info("EXAMS STARTING JOB ACTIVED");
        List<Exam> examList = examService.getAllPendingRecords();
        Date currentState = new Date();
        for (Exam exam : examList) {
            if (exam.getStartDate().before(currentState)) {
                log.info("EXAM: " + exam.getId() + ", STARTED AT: " + new Date());
                exam.setStatus(StatusType.ACTIVE);
                examService.updateExam(exam);
            }
        }
    }

    @Scheduled(fixedDelay = 60 * 1000)
    public void execute(){
        this.endExam();
        this.startExam();
    }
}