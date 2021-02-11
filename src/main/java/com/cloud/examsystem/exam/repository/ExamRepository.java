package com.cloud.examsystem.exam.repository;

import com.cloud.examsystem.exam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam,Long> {
    Optional<Exam> getById(Long id);
}
