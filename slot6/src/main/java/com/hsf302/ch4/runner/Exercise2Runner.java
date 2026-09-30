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
import org.hibernate.LazyInitializationException;
import java.util.Comparator;
import java.util.List;
import com.hsf302.ch4.pojo.Student;
import org.springframework.data.domain.Page;
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
        todo10();
        todo11();
        todo12();
        todo13();
        todo14();
        todo15();
        todo16();
        todo17();
        todo18();
        todo19();
        todo20();
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

    // ===== Helper =====
    // Dùng được cho cả List<Course> và List<Student>
    private <T> void printList(String label, List<T> list) {
        System.out.println("-- " + label + ":");

        for (T item : list) {
            System.out.println("   " + item);
        }

        System.out.println("   -> " + list.size() + " record(s)");
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

    // ===== TODO 10 =====
    private void todo10() {
        title("TODO 10: derived query from inverse side, Distinct");

        printList(
                "(a) Courses of SE002",
                courseService.findCoursesOfStudent("SE002")
        );

        printList(
                "(b1) Courses of AI students - no Distinct",
                courseService.findCoursesOfDepartment("AI", false)
        );

        printList(
                "(b2) Courses of AI students - Distinct",
                courseService.findCoursesOfDepartment("AI", true)
        );
    }

    // ===== TODO 11 =====
    private void todo11() {
        title("TODO 11: IsEmpty, existsBy...And...");

        printList(
                "(a) Students without courses",
                enrollmentService.findStudentsWithoutCourses()
        );

        printList(
                "(b) Courses without students",
                courseService.findCoursesWithoutStudents()
        );

        System.out.println(
                "(c) SE001 enrolled AIL303? "
                        + enrollmentService.isEnrolled("SE001", "AIL303")
        );

        System.out.println(
                "    SE002 enrolled AIL303? "
                        + enrollmentService.isEnrolled("SE002", "AIL303")
        );
    }

    // ===== TODO 12 =====
    private void todo12() {
        title("TODO 12: JPQL JOIN s.courses");

        printList(
                "HSF302 & GPA >= 3.5",
                enrollmentService.findGoodStudentsInCourse("HSF302", 3.5)
        );
    }
    // ===== TODO 13 =====
    private void todo13() {
        title("TODO 13: course statistics (LEFT JOIN + GROUP BY + DTO)");
        printCourseStats();
    }

    private void printCourseStats() {
        courseService.getStatistics().forEach(d -> System.out.printf(
                "   %-6s | %-40s | %d/%d (free %d) | avg GPA %s%n",
                d.code(),
                d.name(),
                d.enrolled(),
                d.capacity(),
                d.remaining(),
                d.avgGpa() == null
                        ? "null"
                        : String.format("%.3f", d.avgGpa())
        ));
    }
    // ===== TODO 14 =====

    private void todo14() {
        title("TODO 14: total credits per student (GROUP BY + HAVING)");

        enrollmentService.getCreditSummary(7).forEach(d -> System.out.printf(
                "   %s | %-15s | %d course(s) | %d credits%n",
                d.studentCode(),
                d.fullName(),
                d.courseCount(),
                d.totalCredits()));
    }
    // ===== TODO 15 =====

    private void todo15() {
        title("TODO 15: SIZE() on collections");

        printList("(a) Full courses", courseService.findFullCourses());

        printList("(b) Students with more than 2 courses",
                enrollmentService.findStudentsWithMoreThan(2));
    }
    // ===== TODO 16 =====
    private void todo16() {
        title("TODO 16: LazyInitializationException, JOIN FETCH, @EntityGraph");

        // (a) Student từ Exercise 1 → transaction đã đóng → courses chưa được nạp
        try {
            Student s = studentService.findByStudentCode("SE001").orElseThrow();
            System.out.println("(a) courses = " + s.getCourses().size());
        } catch (LazyInitializationException e) {
            System.out.println("(a) Caught: " + e.getClass().getSimpleName());
            System.out.println("    " + e.getMessage());
        }

        // (b) JOIN FETCH: nạp student + courses
        Student s = enrollmentService.getStudentWithCourses("SE001");
        System.out.println("(b) " + s.getStudentCode() + " - " + s.getFullName());

        s.getCourses().stream()
                .sorted(Comparator.comparing(Course::getCode))
                .forEach(c -> System.out.println("   " + c));

        // (c) @EntityGraph: nạp course + students
        Course c = courseService.getWithStudents("SWP391");
        System.out.println("(c) " + c.getCode() + " - " + c.getName());

        c.getStudents().stream()
                .sorted(Comparator.comparing(Student::getFullName))
                .forEach(st -> System.out.println("   " + st));
    }
    private void todo17() {
        title("TODO 17: top enrolled courses with native SQL");
        printList("(a) Top enrolled courses", courseService.findTopEnrolledCourses());
    }
    private void todo18() {
        title("TODO 18: interface projection - enrollments of department AI");
        enrollmentService.getEnrollmentsOfDepartment("AI").forEach(v -> System.out.printf(
                "   %s | %-14s | %s | %-35s | %d%n",
                v.getStudentCode(), v.getFullName(), v.getCourseCode(), v.getCourseName(), v.getCredits()));
    }
    private void todo19() {
        title("TODO 19: paginate students of HSF302 (size 2, order by fullName)");
        int pageIndex = 0;
        Page<Student> page;

        do {
            page = enrollmentService.findStudentsInCoursePage("HSF302", pageIndex, 2);
            printList("Page " + pageIndex, page.getContent());
            pageIndex++;
        } while (page.hasNext());

        System.out.println("totalElements = " + page.getTotalElements()
                + ", totalPages = " + page.getTotalPages());
    }
    // ===== TODO 20 =====
    private void todo20() {
        title("TODO 20: enroll with business rules");

        attempt("enroll IA003 -> MKT101",
                () -> enrollmentService.enroll("IA003", "MKT101"));

        attempt("enroll SE001 -> PRJ301",
                () -> enrollmentService.enroll("SE001", "PRJ301"));

        attempt("enroll SE004 -> AIL303",
                () -> enrollmentService.enroll("SE004", "AIL303"));

        attempt("enroll SE003 -> HSF302",
                () -> enrollmentService.enroll("SE003", "HSF302"));

        attempt("enroll XX999 -> HSF302",
                () -> enrollmentService.enroll("XX999", "HSF302"));

        printList("Courses of IA003",
                enrollmentService.getCoursesOfStudent("IA003"));

        System.out.println("Students of MKT101: "
                + enrollmentService.countStudentsInCourse("MKT101"));
    }
    private void attempt(String label, Runnable action) {
        try {
            action.run();
            System.out.println("[OK]   " + label);
        } catch (RuntimeException e) {
            System.out.println("[FAIL] " + label + " -> " + e.getMessage());
        }
    }
}