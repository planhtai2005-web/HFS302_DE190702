package com.hsf302.ch4.repository;

import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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

    // TODO 10a
    List<Student> findByGpaBetweenOrderByGpaDesc(double min, double max);

    // TODO 10b
    List<Student> findByGenderAndActiveTrue(Gender gender);

    // TODO 10c
    List<Student> findByDobAfter(LocalDate date);

    // TODO 11a
    List<Student> findByDepartment_CodeOrderByFullNameAsc(String code);

    // TODO 11b
    long countByDepartment_Code(String code);

    // TODO 11c
    List<Student> findTop3ByOrderByGpaDesc();

    // TODO 12
    @Query("SELECT s FROM Student s " +
            "WHERE s.department.code = :code " +
            "AND s.gpa >= :minGpa " +
            "ORDER BY s.gpa DESC")
    List<Student> findGoodStudentsInDepartment(
            @Param("code") String code,
            @Param("minGpa") double minGpa
    );

    // TODO 13
    @Query("SELECT s FROM Student s " +
            "WHERE LOWER(s.fullName) LIKE LOWER(CONCAT('%', :kw, '%')) " +
            "   OR LOWER(s.email) LIKE LOWER(CONCAT('%', :kw, '%')) " +
            "ORDER BY s.fullName")
    List<Student> searchByKeyword(@Param("kw") String keyword);
    // TODO 14
    @Query("SELECT s FROM Student s " +
            "WHERE s.department.code IN :codes " +
            "ORDER BY s.department.code, s.fullName")
    List<Student> findByDepartmentCodes(@Param("codes") List<String> codes);
}