package com.hsf302.chapter6.repository;

import com.hsf302.chapter6.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {

    boolean existsByCodeIgnoreCase(String code);

    Optional<Major> findByCodeIgnoreCase(String code);
}