package com.cloud.examsystem.grade.entity;

import com.cloud.examsystem.exam.entity.Exam;
import com.cloud.examsystem.exam.model.QuestionAnswerDTO;
import com.cloud.examsystem.user.entity.Student;
import com.cloud.examsystem.user.entity.User;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "es_grade")
@Data
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "db_id")
    private Long dbId;

    @ManyToOne
    private Exam exam;

    @Column(columnDefinition = "json")
    @Type(type = "jsonb")
    private List<QuestionAnswerDTO> solves = new ArrayList<>();

    private Double score;

    @ManyToOne
    private User student;
}

