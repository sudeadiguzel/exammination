package com.cloud.examsystem.exam.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class countPage {
    Integer questionCount;
    Integer optionCount;
}
