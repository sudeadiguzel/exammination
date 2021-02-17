package com.cloud.examsystem.exam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Bean;

import javax.persistence.Entity;
import java.time.OffsetDateTime;
import java.util.Date;

@AllArgsConstructor
@Data
public class countPage {
    Integer questionCount;
    Integer optionCount;
    String startDate;
    String endDate;
    String name;

    public countPage() {

    }
}
