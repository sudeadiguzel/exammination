package com.cloud.examsystem.exam.repository;

import com.cloud.examsystem.exam.entity.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    Optional<Exam> getById(Long id);

    @Query("FROM Exam e where e.status<>'PASSIVE'")
    Page<Exam> findRecords(Pageable p);

    @Query("FROM Exam e where e.user.db_id =:instructorId")
    Page<Exam> findAllRecordsForInstructorWithPaging(Long instructorId, Pageable p);

    @Query("FROM Exam e where e.status='ACTIVE'")
    Page<Exam> findActiveRecordsForStudentWithPaging(Pageable p);

    @Query("FROM Exam e where e.status='ACTIVE' and e.user.db_id=:instructorId")
    Page<Exam> findActiveRecordsForInstructorWithPaging(Long instructorId, Pageable p);

    @Query("FROM Exam e where e.status='ENDED' and e.user.db_id=:instructorId")
    Page<Exam> findCompletedRecordsForInstructorWithPaging(Long instructorId, Pageable p);

    @Query("FROM Exam e where e.status='PASSIVE' and e.user.db_id=:instructorId")
    Page<Exam> findPassiveRecordsForInstructorWithPaging(Long instructorId, Pageable p);

    @Query("FROM Exam e where e.status='PENDING' and e.user.db_id=:instructorId")
    Page<Exam> findPendingRecordsForInstructorWithPaging(Long instructorId, Pageable p);

    @Query("FROM Exam e where e.user.db_id=:id")
    Page<Exam> findAllRecordsForInstructor(Long id, Pageable p);

    @Query("from  Exam e where e.status='ACTIVE'")
    List<Exam> findAllActiveRecords();

    @Query("from  Exam e where e.status='PENDING'")
    List<Exam> findAllPendingRecords();

    @Query("from  Exam e where e.status='PENDING'")
    Page<Exam> findAllPendingRecordsWithPagination(Pageable p);

       @Query("from  Exam e where e.status='ACTIVE'")
    Page<Exam> findAllActiveRecordsWithPagination(Pageable p);

    @Query("from  Exam e where e.status='ENDED'")
    Page<Exam> findAllCompletedRecordsWithPagination(Pageable p);
}
