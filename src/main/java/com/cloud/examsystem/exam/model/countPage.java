package com.cloud.examsystem.exam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Bean;

import javax.persistence.Entity;

@AllArgsConstructor
@Data
@Entity
public class countPage {
    Integer questionCount;
    Integer optionCount;

    public countPage() {

    }
}
