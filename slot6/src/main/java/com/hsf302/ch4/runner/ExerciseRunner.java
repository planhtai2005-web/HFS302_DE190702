
package com.hsf302.ch4.runner;

import com.hsf302.ch4.dto.StudentSummary;
import com.hsf302.ch4.pojo.Department;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.service.DepartmentService;
import com.hsf302.ch4.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.hibernate.LazyInitializationException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        todo17();
        todo18();
        todo19();
    }

    // TODO 11
    private void todo11() {
        System.out.println(
                "===== TODO 11: Nested property / Top / IsEmpty ====="
        );

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
        System.out.println(
                "===== TODO 12: JPQL + named parameter ====="
        );

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
        System.out.println(
                "===== TODO 15: Subquery - GPA above average ====="
        );

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
                    "AI has "
                            + ai.getStudents().size()
                            + " students"
            );
        } catch (LazyInitializationException e) {
            System.out.println(
                    "(a) Caught: "
                            + e.getClass().getSimpleName()
            );

            System.out.println("    " + e.getMessage());
        }

        Department aiFull =
                departmentService.getWithStudents("AI");

        System.out.println("(b) " + aiFull);

        aiFull.getStudents().forEach(s ->
                System.out.println("     " + s)
        );
    }

    // TODO 17
    private void todo17() {
        System.out.println(
                "===== TODO 17: Native query - TOP N ====="
        );

        printList(
                "Top 2 GPA of SE",
                studentService.findTopNInDepartment("SE", 2)
        );
    }

    // TODO 18
    private void todo18() {
        System.out.println(
                "===== TODO 18: Interface projection ====="
        );

        List<StudentSummary> list =
                studentService.getActiveSummaries();

        list.forEach(p ->
                System.out.printf(
                        "   %s | %-15s | %.1f | %s%n",
                        p.getStudentCode(),
                        p.getFullName(),
                        p.getGpa(),
                        p.getDepartmentName()
                )
        );

        System.out.println(
                "   -> " + list.size() + " record(s)"
        );
    }

    // TODO 19
    private void todo19() {
        System.out.println(
                "===== TODO 19: Pagination active students ====="
        );

        Page<Student> page0 =
                studentService.findActiveByDepartment(
                        "SE",
                        PageRequest.of(0, 2)
                );

        Page<Student> page1 =
                studentService.findActiveByDepartment(
                        "SE",
                        PageRequest.of(1, 2)
                );

        System.out.println("Page 0:");
        page0.getContent().forEach(s ->
                System.out.println("   " + s)
        );

        System.out.println("Page 1:");
        page1.getContent().forEach(s ->
                System.out.println("   " + s)
        );

        System.out.println(
                "totalElements = " + page0.getTotalElements()
        );

        System.out.println(
                "totalPages = " + page0.getTotalPages()
        );
    }

    private void printList(String title, List<?> list) {
        System.out.println(title);

        for (Object item : list) {
            System.out.println("  " + item);
        }
    }
    // TODO 20
    private void todo20() {
        System.out.println(
                "===== TODO 20: Update GPA ====="
        );

        int updated =
                studentService.updateGpa("SE001", 3.4);

        System.out.println(
                "Updated rows = " + updated
        );

        studentService.findByStudentCode("SE001")
                .ifPresent(s ->
                        System.out.println("   " + s)
                );
    }
    // TODO 21
    private void todo21() {
        System.out.println(
                "===== TODO 21: Deactivate students below GPA ====="
        );

        int updated =
                studentService.deactivateStudentsBelowGpa(2.5);

        System.out.println(
                "Updated rows = " + updated
        );

        System.out.println(
                "Active students = "
                        + studentService.countActive()
        );
    }
}