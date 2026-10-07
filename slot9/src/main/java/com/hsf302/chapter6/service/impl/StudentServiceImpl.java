package com.hsf302.chapter6.service.impl;

import com.hsf302.chapter6.entity.Student;
import com.hsf302.chapter6.repository.StudentRepository;
import com.hsf302.chapter6.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hsf302.chapter6.dto.StudentForm;
import java.util.List;
import java.util.Optional;
import com.hsf302.chapter6.entity.Major;
import com.hsf302.chapter6.repository.MajorRepository;
@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final MajorRepository majorRepository;
    public StudentServiceImpl(
            StudentRepository studentRepository,
            MajorRepository majorRepository) {

        this.studentRepository = studentRepository;
        this.majorRepository = majorRepository;
    }
    @Override
    public List<Student> findAll() {
        return studentRepository.findAll(
                Sort.by(Sort.Direction.ASC, "id")
        );
    }

    @Override
    public List<Student> search(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }

        String value = keyword.trim();

        return studentRepository
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        value,
                        value,
                        Sort.by(Sort.Direction.ASC, "id")
                );
    }

    @Override
    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public Page<Student> search(
            String keyword,
            Pageable pageable) {

        if (keyword == null || keyword.isBlank()) {
            return findAll(pageable);
        }

        String value = keyword.trim();

        return studentRepository
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        value,
                        value,
                        pageable
                );
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    @Transactional
    public Student create(Student student) {
        student.setId(null);
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public boolean update(Long id, Student data) {
        return studentRepository.findById(id)
                .map(existing -> {
                    existing.setName(data.getName());
                    existing.setEmail(data.getEmail());
                    existing.setAge(data.getAge());
                    existing.setMajor(data.getMajor());
                    existing.setGpa(data.getGpa());

                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (!studentRepository.existsById(id)) {
            return false;
        }

        studentRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean isEmailTaken(
            String email,
            Long excludeId) {

        if (email == null || email.isBlank()) {
            return false;
        }

        return excludeId == null
                ? studentRepository.existsByEmailIgnoreCase(
                email.trim())
                : studentRepository.existsByEmailIgnoreCaseAndIdNot(
                email.trim(),
                excludeId
        );
    }

    @Override
    public List<Major> getMajors() {
        return majorRepository.findAll(
                Sort.by(Sort.Direction.ASC, "code")
        );
    }
    @Override
    public StudentForm toForm(Student student) {

        StudentForm form = new StudentForm();

        form.setId(student.getId());
        form.setName(student.getName());
        form.setEmail(student.getEmail());
        form.setAge(student.getAge());

        if (student.getMajor() != null) {
            form.setMajorId(student.getMajor().getId());
        }

        form.setGpa(student.getGpa());

        return form;
    }
    @Override
    public Student toEntity(StudentForm form) {

        Student student = new Student();

        student.setId(form.getId());
        student.setName(form.getName());
        student.setEmail(form.getEmail());
        student.setAge(form.getAge());

        Major major = majorRepository
                .findById(form.getMajorId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Không tìm thấy chuyên ngành"
                        ));

        student.setMajor(major);
        student.setGpa(form.getGpa());

        return student;
    }
}