package com.hsf302.ch4.specification;

import com.hsf302.ch4.pojo.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    private StudentSpecification() {
    }

    public static Specification<Student> hasDepartment(
            String departmentCode
    ) {
        return (root, query, cb) -> {
            if (departmentCode == null || departmentCode.isBlank()) {
                return null;
            }

            return cb.equal(
                    root.get("department").get("code"),
                    departmentCode
            );
        };
    }

    public static Specification<Student> gpaGreaterThanOrEqual(
            Double minGpa
    ) {
        return (root, query, cb) -> {
            if (minGpa == null) {
                return null;
            }

            return cb.greaterThanOrEqualTo(
                    root.get("gpa"),
                    minGpa
            );
        };
    }

    public static Specification<Student> isActive(
            Boolean active
    ) {
        return (root, query, cb) -> {
            if (active == null) {
                return null;
            }

            return cb.equal(
                    root.get("active"),
                    active
            );
        };
    }

    public static Specification<Student> nameContains(
            String keyword
    ) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) {
                return null;
            }

            return cb.like(
                    cb.lower(root.get("fullName")),
                    "%" + keyword.toLowerCase() + "%"
            );
        };
    }
}