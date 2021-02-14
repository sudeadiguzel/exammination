package com.cloud.examsystem.exam.repository;

import com.cloud.examsystem.exam.common.StatusType;
import com.cloud.examsystem.exam.entity.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam,Long> {
    Optional<Exam> getById(Long id);

    @Query("FROM Exam e")
    Page<Exam> findRecords(Pageable p);

    @Query("FROM Exam e where e.user.db_id=:id and e.status=:status")
    Page<Exam> findActiveRecordsForInstructorByStatus(Long id, StatusType status,Pageable p);
    @Query("FROM Exam e where e.user.db_id=:id")
    Page<Exam> findAllRecordsForInstructor(Long id,Pageable p);

    @Query("from  Exam e where e.status='ACTIVE'")
    List<Exam> findAllActiveRecords();
    @Query("from  Exam e where e.status='PENDING'")
    List<Exam> findAllPendingRecords();
}
