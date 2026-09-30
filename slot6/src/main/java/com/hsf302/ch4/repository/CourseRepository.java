package com.hsf302.ch4.repository;

import com.hsf302.ch4.pojo.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // ===== TODO 7 =====
    Optional<Course> findByCode(String code);

    // ===== TODO 8 =====
    List<Course> findBySemesterOrderByCodeAsc(String semester);

    long countBySemester(String semester);

    // ===== TODO 10 =====
    List<Course> findByStudents_StudentCodeOrderByCodeAsc(String studentCode);

    List<Course> findByStudents_Department_CodeOrderByCodeAsc(String deptCode);

    List<Course> findDistinctByStudents_Department_CodeOrderByCodeAsc(String deptCode);
}