package com.cloud.examsystem.user.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "es_user")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long db_id;

    private String name;
    private String surname;
    private String email;
    private String password;
    private String username;
    private boolean instructor;


}
