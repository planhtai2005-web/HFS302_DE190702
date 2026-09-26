package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public long count() {
        return studentRepository.count();
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    // TODO 7a
    @Override
    public List<Student> findAllOrderByGpaDesc() {
        return studentRepository.findAll(
                Sort.by(Sort.Direction.DESC, "gpa")
        );
    }

    // TODO 7b
    @Override
    public Page<Student> findPage(int pageIndex, int size, String sortField) {
        if (pageIndex < 0 || size <= 0) {
            throw new IllegalArgumentException(
                    "pageIndex phải >= 0 và size phải > 0"
            );
        }

        Pageable pageable = PageRequest.of(
                pageIndex,
                size,
                Sort.by(sortField).ascending()
        );

        return studentRepository.findAll(pageable);
    }

    // TODO 8a
    @Override
    public Optional<Student> findByStudentCode(String studentCode) {
        return studentRepository.findByStudentCode(studentCode);
    }

    // TODO 8b
    @Override
    public boolean isEmailExisted(String email) {
        return studentRepository.existsByEmail(email);
    }

    // TODO 8c
    @Override
    public long countActive() {
        return studentRepository.countByActiveTrue();
    }

    // TODO 9a
    @Override
    public List<Student> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }

        return studentRepository.findByFullNameContainingIgnoreCase(
                keyword.trim()
        );
    }

    // TODO 9b
    @Override
    public List<Student> findByEmailDomain(String domain) {
        String suffix = domain.startsWith("@")
                ? domain
                : "@" + domain;

        return studentRepository.findByEmailEndingWith(suffix);
    }

    // TODO 9c
    @Override
    public List<Student> findWithoutEmail() {
        return studentRepository.findByEmailIsNull();
    }

    // TODO 10a
    @Override
    public List<Student> findByGpaRange(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException(
                    "min phải <= max"
            );
        }

        return studentRepository.findByGpaBetweenOrderByGpaDesc(min, max);
    }

    // TODO 10b
    @Override
    public List<Student> findActiveByGender(Gender gender) {
        return studentRepository.findByGenderAndActiveTrue(gender);
    }

    // TODO 10c
    @Override
    public List<Student> findBornAfter(LocalDate date) {
        return studentRepository.findByDobAfter(date);
    }
    // TODO 11a
    @Override
    public List<Student> findByDepartment(String deptCode) {
        return studentRepository.findByDepartment_CodeOrderByFullNameAsc(deptCode);
    }

    // TODO 11b
    @Override
    public long countByDepartment(String deptCode) {
        return studentRepository.countByDepartment_Code(deptCode);
    }

    // TODO 11c
    @Override
    public List<Student> findTop3ByGpa() {
        return studentRepository.findTop3ByOrderByGpaDesc();
    }
    // TODO 12
    @Override
    public List<Student> findGoodStudents(String deptCode, double minGpa) {
        return studentRepository.findGoodStudentsInDepartment(deptCode, minGpa);
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
        if (codes == null || codes.isEmpty()) {
            return List.of();
        }

        return studentRepository.findByDepartmentCodes(codes);
    }
}