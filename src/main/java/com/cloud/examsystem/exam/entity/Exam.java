package com.cloud.examsystem.exam.entity;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import javax.persistence.*;
import java.util.HashMap;

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
    private HashMap<String,Object> question;

}
