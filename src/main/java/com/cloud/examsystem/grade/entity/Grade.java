package com.cloud.examsystem.grade.entity;

import com.cloud.examsystem.exam.entity.Exam;
import com.cloud.examsystem.user.entity.Student;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
    private Map<String, Object> solves;

    private Double score;

    @ManyToOne
    private Student student;
}

