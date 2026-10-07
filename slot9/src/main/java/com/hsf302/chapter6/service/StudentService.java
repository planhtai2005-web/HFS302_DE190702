package com.hsf302.chapter6.service;

import com.hsf302.chapter6.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> findAll();

    List<Student> search(String keyword);

    Page<Student> findAll(Pageable pageable);

    Page<Student> search(String keyword, Pageable pageable);

    Optional<Student> findById(Long id);

    Student create(Student student);

    boolean update(Long id, Student data);

    boolean delete(Long id);

    boolean isEmailTaken(String email, Long excludeId);

    List<String> getMajors();
}