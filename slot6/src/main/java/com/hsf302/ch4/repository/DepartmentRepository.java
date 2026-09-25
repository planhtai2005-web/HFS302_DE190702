package com.hsf302.ch4.repository;

import com.hsf302.ch4.pojo.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // TODO 11a
    Optional<Department> findByCode(String code);

    // TODO 11b
    List<Department> findByStudentsIsEmpty();
}