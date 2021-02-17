package com.cloud.examsystem.exam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Option implements Serializable {
    private String content;
    private Boolean correct = Boolean.FALSE;
    private int index;
    public Option(int index){
        this.index = index;
    }
}
