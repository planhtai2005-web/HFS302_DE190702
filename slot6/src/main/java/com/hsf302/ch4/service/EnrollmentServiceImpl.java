package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Course;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.repository.CourseRepository;
import com.hsf302.ch4.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollmentServiceImpl implements EnrollmentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<Course> getCoursesOfStudent(String studentCode) {
        Student student = studentRepository
                .findByStudentCode(studentCode)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Student not found: " + studentCode
                        )
                );

        return List.copyOf(student.getCourses());
    }

    @Override
    public List<Student> getStudentsOfCourse(String courseCode) {
        Course course = courseRepository
                .findByCode(courseCode)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Course not found: " + courseCode
                        )
                );

        return List.copyOf(course.getStudents());
    }
}