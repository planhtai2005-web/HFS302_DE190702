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
import java.util.List;
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
        todo7();
        todo8();
        todo9();
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
    private void todo7() {
        title("TODO 7: bidirectional navigation");

        System.out.println("-- Courses of student SE001:");
        enrollmentService.getCoursesOfStudent("SE001")
                .forEach(course -> System.out.println("   " + course));

        System.out.println("-- Students of course HSF302:");
        enrollmentService.getStudentsOfCourse("HSF302")
                .forEach(student -> System.out.println("   " + student));
    }
    // ===== TODO 8 =====
    private void todo8() {
        title("TODO 8: findByCode, findBySemester, countBySemester");

        for (String code : List.of("HSF302", "XXX000")) {
            System.out.println(
                    "(a) " + code + ": "
                            + courseService.findByCode(code)
                            .map(Course::getName)
                            .orElse("Not found")
            );
        }

        printList(
                "(b) Semester SU26",
                courseService.findBySemester("SU26")
        );

        System.out.println(
                "(c) Courses in FA26: "
                        + courseService.countBySemester("FA26")
        );
    }
    // ===== TODO 9 =====
    private void todo9() {
        title("TODO 9: derived query through collection courses");

        printList(
                "(a) Students of PRJ301",
                enrollmentService.findStudentsInCourse("PRJ301")
        );

        System.out.println(
                "(b) Students of HSF302: "
                        + enrollmentService.countStudentsInCourse("HSF302")
        );

        printList(
                "(c) Active students of PRJ301",
                enrollmentService.findActiveStudentsInCourse("PRJ301")
        );
    }
}