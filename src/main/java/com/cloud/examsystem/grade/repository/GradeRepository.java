package com.cloud.examsystem.grade.repository;

import com.cloud.examsystem.grade.entity.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade,Long> {


    Grade getByDbId(Long id);

    @Query("FROM Grade g")
    Page<Grade> findActiveRecordsbyExamId(Pageable p);

    @Query("FROM Grade g where g.exam.id=:id")
    Page<Grade> findActiveRecords(Long id,Pageable p);

    @Query("FROM Grade g where g.dbId=:gradeId and g.student.db_id=:id and g.student.instructor=false")
    Page<Grade> findStudentGrades(Long id,Long gradeId,Pageable p);
}
