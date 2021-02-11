package com.cloud.examsystem.exam.entity;

import com.cloud.examsystem.exam.model.Question;
import com.cloud.examsystem.user.entity.User;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "es_exam")
@Data
@TypeDef(
        name = "jsonb",
        typeClass = JsonBinaryType.class
)
public class Exam {
    private final static String jsonb = "jsonb";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Type(type = "jsonb")
    @Column(columnDefinition = "json")
    private List<Question> question = new ArrayList<>();

    private String name;

    @ManyToOne
    private User user;

    private String description;

}
