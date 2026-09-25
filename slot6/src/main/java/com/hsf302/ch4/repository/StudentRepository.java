package com.hsf302.ch4.repository;

import com.hsf302.ch4.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface StudentRepository
        extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    // TODO 8a
    Optional<Student> findByStudentCode(String studentCode);

    // TODO 8b
    boolean existsByEmail(String email);

    // TODO 8c
    long countByActiveTrue();

    // TODO 9a
    List<Student> findByFullNameContainingIgnoreCase(String keyword);

    // TODO 9b
    List<Student> findByEmailEndingWith(String suffix);

    // TODO 9c
    List<Student> findByEmailIsNull();
}