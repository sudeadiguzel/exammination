package com.cloud.examsystem.exam.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExamDTO {
    List<QuestionDTO> question;
}
