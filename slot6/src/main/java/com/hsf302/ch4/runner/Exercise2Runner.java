package com.hsf302.ch4.runner;

import com.hsf302.ch4.service.CourseService;
import com.hsf302.ch4.service.EnrollmentService;
import com.hsf302.ch4.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@Profile("ex2")
@RequiredArgsConstructor
public class Exercise2Runner implements CommandLineRunner {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final StudentService studentService;

    @Override
    public void run(String... args) {
        System.out.println(">>> Exercise 2 runner started");
    }
}