package com.cloud.examsystem.exam.entity;

import com.cloud.examsystem.exam.common.StatusType;
import com.cloud.examsystem.exam.model.Question;
import com.cloud.examsystem.user.entity.User;
import javassist.NotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "es_exam")
@Data
@TypeDef(
        name = "jsonb",
        typeClass = JsonBinaryType.class
)
@NoArgsConstructor
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

    private String description="";

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date startDate;
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date endDate;
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date creationTimestamp;

    private Integer applicationCount;


    @Enumerated(EnumType.STRING)
    private StatusType status = StatusType.PASSIVE;

    public Exam(int questionCount,int optionCount){
       for (int i= 0; i<questionCount;i++){
            this.question.add(new Question(optionCount,i));
        }
    }

    public Question getQuestionByQuestionNumber(int questionNumber){
        for (Question q:question){
            if (q.getNumber()==questionNumber){
                return q;
            }
        }
        return null;
    }



}
