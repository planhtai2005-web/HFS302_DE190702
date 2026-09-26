package com.hsf302.ch4.service;

import com.hsf302.ch4.dto.StudentSummary;
import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    // TODO 6
    @Override
    public long count() {
        return studentRepository.count();
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return studentRepository.existsById(id);
    }

    // TODO 7
    @Override
    public List<Student> findAllSorted(String sortBy) {
        return studentRepository.findAll(
                org.springframework.data.domain.Sort.by(sortBy)
        );
    }

    @Override
    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    // TODO 8
    @Override
    public Optional<Student> findByStudentCode(String studentCode) {
        return studentRepository.findByStudentCode(studentCode);
    }

    @Override
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    public long countActive() {
        return studentRepository.countByActiveTrue();
    }

    // TODO 9
    @Override
    public List<Student> findByFullNameContainingIgnoreCase(String keyword) {
        return studentRepository.findByFullNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<Student> findByEmailEndingWith(String domain) {
        return studentRepository.findByEmailEndingWith(domain);
    }

    @Override
    public List<Student> findByEmailIsNull() {
        return studentRepository.findByEmailIsNull();
    }

    // TODO 10
    @Override
    public List<Student> findByGpaRange(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("min GPA phải <= max GPA");
        }

        return studentRepository.findByGpaBetweenOrderByGpaDesc(min, max);
    }

    @Override
    public List<Student> findActiveByGender(Gender gender) {
        return studentRepository.findByGenderAndActiveTrue(gender);
    }

    @Override
    public List<Student> findBornAfter(LocalDate date) {
        return studentRepository.findByDobAfter(date);
    }

    // TODO 11
    @Override
    public List<Student> findByDepartment(String deptCode) {
        return studentRepository.findByDepartment_CodeOrderByFullNameAsc(deptCode);
    }

    @Override
    public long countByDepartment(String deptCode) {
        return studentRepository.countByDepartment_Code(deptCode);
    }

    @Override
    public List<Student> findTop3ByGpa() {
        return studentRepository.findTop3ByOrderByGpaDesc();
    }

    // TODO 12
    @Override
    public List<Student> findGoodStudents(
            String deptCode,
            double minGpa
    ) {
        return studentRepository.findGoodStudentsInDepartment(
                deptCode,
                minGpa
        );
    }

    // TODO 13
    @Override
    public List<Student> searchByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }

        return studentRepository.searchByKeyword(keyword.trim());
    }

    // TODO 14
    @Override
    public List<Student> findByDepartmentCodes(List<String> codes) {
        return studentRepository.findByDepartmentCodes(codes);
    }

    // TODO 15
    @Override
    public List<Student> findAboveAverageGpa() {
        return studentRepository.findAboveAverageGpa();
    }

    // TODO 17
    @Override
    public List<Student> findTopNInDepartment(
            String deptCode,
            int n
    ) {
        if (n <= 0) {
            throw new IllegalArgumentException("n phải > 0");
        }

        return studentRepository.findTopNByDepartmentNative(
                deptCode,
                n
        );
    }

    // TODO 18
    @Override
    public List<StudentSummary> getActiveSummaries() {
        return studentRepository.findActiveSummaries();
    }
    // TODO 19
    @Override
    public Page<Student> findActiveByDepartment(
            String deptCode,
            Pageable pageable
    ) {
        return studentRepository.findActiveByDepartment(
                deptCode,
                pageable
        );
    }
}