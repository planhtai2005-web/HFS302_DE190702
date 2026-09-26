package com.hsf302.ch4.runner;

import com.hsf302.ch4.pojo.Department;
import com.hsf302.ch4.service.DepartmentService;
import com.hsf302.ch4.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.hibernate.LazyInitializationException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
@RequiredArgsConstructor
public class ExerciseRunner implements CommandLineRunner {

    private final DepartmentService departmentService;
    private final StudentService studentService;

    @Override
    public void run(String... args) {
        todo11();
        todo12();
        todo13();
        todo14();
        todo15();
        todo16();
    }

    // TODO 11
    private void todo11() {
        System.out.println("===== TODO 11: Nested property / Top / IsEmpty =====");

        printList(
                "Students of SE (order by name)",
                studentService.findByDepartment("SE")
        );

        System.out.println(
                "count students of AI -> "
                        + studentService.countByDepartment("AI")
        );

        printList(
                "Top 3 GPA",
                studentService.findTop3ByGpa()
        );

        printList(
                "Departments without students",
                departmentService.findDepartmentsWithoutStudents()
        );
    }

    // TODO 12
    private void todo12() {
        System.out.println("===== TODO 12: JPQL + named parameter =====");

        printList(
                "SE, GPA >= 3.0",
                studentService.findGoodStudents("SE", 3.0)
        );
    }

    // TODO 13
    private void todo13() {
        System.out.println("===== TODO 13: JPQL LIKE =====");

        printList(
                "keyword 'hoa'",
                studentService.searchByKeyword("hoa")
        );

        printList(
                "keyword 'gmail'",
                studentService.searchByKeyword("gmail")
        );
    }

    // TODO 14
    private void todo14() {
        System.out.println("===== TODO 14: JPQL IN =====");

        printList(
                "Departments SE + AI",
                studentService.findByDepartmentCodes(
                        List.of("SE", "AI")
                )
        );
    }

    // TODO 15
    private void todo15() {
        System.out.println("===== TODO 15: Subquery - GPA above average =====");

        printList(
                "GPA > AVG",
                studentService.findAboveAverageGpa()
        );
    }

    // TODO 16
    private void todo16() {
        System.out.println(
                "===== TODO 16: LazyInitializationException & JOIN FETCH ====="
        );

        Department ai = departmentService
                .findByCode("AI")
                .orElseThrow();

        try {
            System.out.println(
                    "AI has " + ai.getStudents().size() + " students"
            );
        } catch (LazyInitializationException e) {
            System.out.println(
                    "(a) Caught: " + e.getClass().getSimpleName()
            );

            System.out.println(
                    "    " + e.getMessage()
            );
        }

        Department aiFull = departmentService.getWithStudents("AI");

        System.out.println("(b) " + aiFull);

        aiFull.getStudents().forEach(
                s -> System.out.println("     " + s)
        );
    }

    private void printList(String title, List<?> list) {
        System.out.println(title);

        for (Object item : list) {
            System.out.println("  " + item);
        }
    }
}