package com.hsf302.ch4.runner;

import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.service.DepartmentService;
import com.hsf302.ch4.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
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
        todo7();
    }

    private void todo7() {
        System.out.println("===== TODO 7: Sort & Pageable =====");

        // (a) GPA giảm dần
        printList(
                "All students order by GPA desc",
                studentService.findAllOrderByGpaDesc()
        );

        // (b) Trang thứ 2 -> index 1
        Page<Student> page = studentService.findPage(1, 3, "fullName");

        printList(
                "Page index " + page.getNumber()
                        + " (size " + page.getSize() + ")",
                page.getContent()
        );

        System.out.println(
                "totalElements=" + page.getTotalElements()
                        + ", totalPages=" + page.getTotalPages()
                        + ", hasNext=" + page.hasNext()
                        + ", hasPrevious=" + page.hasPrevious()
        );
    }

    private void printList(String title, List<Student> students) {
        System.out.println(title);

        for (Student student : students) {
            System.out.println("  " + student);
        }
    }
}