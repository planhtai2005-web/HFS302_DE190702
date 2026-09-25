package com.hsf302.ch4.runner;

import com.hsf302.ch4.pojo.Student;
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
        todo9();
    }

    private void todo9() {
        System.out.println("===== TODO 9: Containing / EndingWith / IsNull =====");

        printList(
                "fullName contains 'nguyen'",
                studentService.searchByName("nguyen")
        );

        printList(
                "email domain 'gmail.com'",
                studentService.findByEmailDomain("gmail.com")
        );

        printList(
                "email is null",
                studentService.findWithoutEmail()
        );
    }

    private void printList(String title, List<Student> students) {
        System.out.println(title);

        for (Student student : students) {
            System.out.println("  " + student);
        }
    }
}