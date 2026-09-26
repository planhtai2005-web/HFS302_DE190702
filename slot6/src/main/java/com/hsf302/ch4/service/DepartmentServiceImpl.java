package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Department;
import com.hsf302.ch4.repository.DepartmentRepository;
import com.hsf302.ch4.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;

    @Override
    public long count() {
        return departmentRepository.count();
    }

    @Override
    public boolean existsById(Long id) {
        return departmentRepository.existsById(id);
    }

    // TODO 11d
    @Override
    public List<Department> findDepartmentsWithoutStudents() {
        return departmentRepository.findByStudentsIsEmpty();
    }

    // TODO 16a
    @Override
    public Optional<Department> findByCode(String code) {
        return departmentRepository.findByCode(code);
    }

    // TODO 16b
    @Override
    public Department getWithStudents(String code) {
        return departmentRepository.findByCodeWithStudents(code)
                .orElseThrow(() ->
                        new IllegalArgumentException("Department not found: " + code));
    }
}