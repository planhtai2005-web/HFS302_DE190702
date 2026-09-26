package com.hsf302.ch4.service;

import com.hsf302.ch4.dto.StudentSummary;
import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    // TODO 6
    long count();

    Optional<Student> findById(Long id);

    boolean existsById(Long id);

    // TODO 7
    List<Student> findAllSorted(String sortBy);

    Page<Student> findAll(Pageable pageable);

    // TODO 8
    Optional<Student> findByStudentCode(String studentCode);

    boolean existsByEmail(String email);

    long countActive();

    // TODO 9
    List<Student> findByFullNameContainingIgnoreCase(String keyword);

    List<Student> findByEmailEndingWith(String domain);

    List<Student> findByEmailIsNull();

    List<Student> findByGpaRange(double min, double max);
    List<Student> findActiveByGender(Gender gender);
    List<Student> findBornAfter(LocalDate date);
    // TODO 11
    List<Student> findByDepartment(String deptCode);

    long countByDepartment(String deptCode);

    List<Student> findTop3ByGpa();

    // TODO 12
    List<Student> findGoodStudents(String deptCode, double minGpa);

    // TODO 13
    List<Student> searchByKeyword(String keyword);

    // TODO 14
    List<Student> findByDepartmentCodes(List<String> codes);

    // TODO 15
    List<Student> findAboveAverageGpa();

    // TODO 17
    List<Student> findTopNInDepartment(String deptCode, int n);

    // TODO 18
    List<StudentSummary> getActiveSummaries();
    // TODO 19
    Page<Student> findActiveByDepartment(String deptCode, Pageable pageable);
}