package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Student;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    long count();

    Optional<Student> findById(Long id);

    // TODO 7a
    List<Student> findAllOrderByGpaDesc();

    // TODO 7b
    Page<Student> findPage(int pageIndex, int size, String sortField);
}