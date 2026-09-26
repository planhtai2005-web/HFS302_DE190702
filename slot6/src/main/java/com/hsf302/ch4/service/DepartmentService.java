package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    long count();

    boolean existsById(Long id);

    // TODO 11d
    List<Department> findDepartmentsWithoutStudents();

    // TODO 16a
    Optional<Department> findByCode(String code);

    // TODO 16b
    Department getWithStudents(String code);
}