package com.cloud.examsystem;

import com.cloud.examsystem.exam.controller.ExamConverter;
import com.cloud.examsystem.exam.model.countPage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class ExamSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamSystemApplication.class, args);
    }

}
