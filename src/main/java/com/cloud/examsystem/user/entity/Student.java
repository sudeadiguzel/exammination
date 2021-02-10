package com.cloud.examsystem.user.entity;

import com.cloud.examsystem.grade.entity.Grade;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Student extends User{

    @OneToMany
    @JoinColumn(name = "db_id")
    private List<Grade> examApplication;

}
