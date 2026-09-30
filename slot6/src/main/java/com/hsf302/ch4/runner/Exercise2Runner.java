package com.hsf302.ch4.runner;

import com.hsf302.ch4.pojo.Course;
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

        todo6();
    }

    private void todo6() {
        title("TODO 6: count, findAll(Sort), findById");

        System.out.println("Total courses: " + courseService.count());

        printList(
                "All courses order by code",
                courseService.findAllOrderByCode()
        );

        for (long id : new long[]{2L, 99L}) {
            System.out.println(
                    "findById(" + id + "): "
                            + courseService.findById(id)
                            .map(Course::toString)
                            .orElse("Not found")
            );
        }
    }

    private void title(String text) {
        System.out.println();
        System.out.println("=== " + text + " ===");
    }

    private void printList(String label, java.util.List<Course> courses) {
        System.out.println("-- " + label + ":");

        for (Course course : courses) {
            System.out.println("   " + course);
        }

        System.out.println("   -> " + courses.size() + " record(s)");
    }
}