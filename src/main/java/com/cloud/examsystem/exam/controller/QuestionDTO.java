package com.cloud.examsystem.exam.controller;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionDTO {
    private List questionText;
    private List option;
    private List optionAnswer;
}
