package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    long count();

    Optional<Student> findById(Long id);

    // TODO 7a
    List<Student> findAllOrderByGpaDesc();

    // TODO 7b
    Page<Student> findPage(int pageIndex, int size, String sortField);

    // TODO 8a
    Optional<Student> findByStudentCode(String studentCode);

    // TODO 8b
    boolean isEmailExisted(String email);

    // TODO 8c
    long countActive();

    // TODO 9a
    List<Student> searchByName(String keyword);

    // TODO 9b
    List<Student> findByEmailDomain(String domain);

    // TODO 9c
    List<Student> findWithoutEmail();

    // TODO 10a
    List<Student> findByGpaRange(double min, double max);

    // TODO 10b
    List<Student> findActiveByGender(Gender gender);

    // TODO 10c
    List<Student> findBornAfter(LocalDate date);
}