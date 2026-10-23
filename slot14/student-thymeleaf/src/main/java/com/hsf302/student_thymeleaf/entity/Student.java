package com.hsf302.student_thymeleaf.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "Students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_code", nullable = false, length = 8, unique = true)
    private String studentCode;

    @Nationalized
    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "gpa", nullable = false)
    private Double gpa;

    @Column(name = "major", nullable = false, length = 2)
    private String major;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    public Student() {
    }

    public Student(Long id, String studentCode, String fullName, String email,
                   LocalDate dateOfBirth, Double gpa, String major, boolean active) {
        this.id = id;
        this.studentCode = studentCode;
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gpa = gpa;
        this.major = major;
        this.active = active;
    }
}