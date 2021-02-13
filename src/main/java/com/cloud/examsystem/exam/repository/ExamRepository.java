package com.cloud.examsystem.exam.repository;

import com.cloud.examsystem.exam.entity.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam,Long> {
    Optional<Exam> getById(Long id);

    @Query("FROM Exam e")
    Page<Exam> findActiveRecords(Pageable p);

    @Query("FROM Exam e where e.user.db_id=:id and e.user.instructor=true")
    Page<Exam> findActiveRecordsForInstructor(Long id,Pageable p);

}
