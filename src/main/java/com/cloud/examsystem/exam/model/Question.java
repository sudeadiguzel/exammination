package com.cloud.examsystem.exam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question implements Serializable {
    private List<Option> optionList = new ArrayList<>();
    private String description;
    private Integer number;

}
