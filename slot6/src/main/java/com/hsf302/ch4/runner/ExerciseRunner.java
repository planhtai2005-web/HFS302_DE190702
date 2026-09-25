package com.hsf302.ch4.runner;

import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.service.DepartmentService;
import com.hsf302.ch4.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Order(2)
@RequiredArgsConstructor
public class ExerciseRunner implements CommandLineRunner {

    private final DepartmentService departmentService;
    private final StudentService studentService;

    @Override
    public void run(String... args) {
        todo10();
    }

    private void todo10() {
        System.out.println("===== TODO 10: Between / Gender / DobAfter =====");

        printList(
                "GPA from 2.0 to 3.5",
                studentService.findByGpaRange(2.0, 3.5)
        );

        printList(
                "active female students",
                studentService.findActiveByGender(Gender.FEMALE)
        );

        printList(
                "born after 2004-01-01",
                studentService.findBornAfter(
                        LocalDate.of(2004, 1, 1)
                )
        );
    }

    private void printList(String title, List<Student> students) {
        System.out.println(title);

        for (Student student : students) {
            System.out.println("  " + student);
        }
    }
}