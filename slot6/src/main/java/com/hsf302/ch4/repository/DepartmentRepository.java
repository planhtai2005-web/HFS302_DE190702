package com.hsf302.ch4.repository;

import com.hsf302.ch4.pojo.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // TODO 11a
    Optional<Department> findByCode(String code);

    // TODO 11b
    List<Department> findByStudentsIsEmpty();

    // TODO 16
    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.students WHERE d.code = :code")
    Optional<Department> findByCodeWithStudents(@Param("code") String code);
}