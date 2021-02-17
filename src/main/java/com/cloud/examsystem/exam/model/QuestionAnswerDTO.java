package com.cloud.examsystem.exam.model;

import lombok.Data;

import java.util.List;

@Data
public class QuestionAnswerDTO {
    private Integer questionNumber;
    private List<Option> answers;
    private boolean correct;
    private Integer studentScore;
}
