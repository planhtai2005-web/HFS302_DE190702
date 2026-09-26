package com.hsf302.ch4.runner;

import com.hsf302.ch4.service.DepartmentService;
import com.hsf302.ch4.service.StudentService;
import lombok.RequiredArgsConstructor;
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
    }

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

    private void printList(String title, List<?> list) {
        System.out.println(title);

        for (Object item : list) {
            System.out.println("  " + item);
        }
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
}