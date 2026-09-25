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
        todo8();
    }

    private void todo8() {
        System.out.println("===== TODO 8: findBy / existsBy / countBy =====");

        for (String code : List.of("AI002", "XX999")) {
            System.out.println(
                    "findByStudentCode(" + code + ") -> " +
                            studentService.findByStudentCode(code)
                                    .map(Object::toString)
                                    .orElse("Not found")
            );
        }

        System.out.println(
                "isEmailExisted(binh.tt@fpt.edu.vn) -> "
                        + studentService.isEmailExisted("binh.tt@fpt.edu.vn")
        );

        System.out.println(
                "countActive -> " + studentService.countActive()
        );
    }
}