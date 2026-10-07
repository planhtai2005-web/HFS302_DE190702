package com.hsf302.chapter6.config;

import com.hsf302.chapter6.entity.Major;
import com.hsf302.chapter6.entity.Student;
import com.hsf302.chapter6.repository.MajorRepository;
import com.hsf302.chapter6.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            StudentRepository studentRepository,
            MajorRepository majorRepository) {

        return args -> {

            if (studentRepository.count() == 0) {

                Major cntt = majorRepository
                        .findByCodeIgnoreCase("CNTT")
                        .orElseThrow();

                Major ktpm = majorRepository
                        .findByCodeIgnoreCase("KTPM")
                        .orElseThrow();

                Major attt = majorRepository
                        .findByCodeIgnoreCase("ATTT")
                        .orElseThrow();

                Major httt = majorRepository
                        .findByCodeIgnoreCase("HTTT")
                        .orElseThrow();

                studentRepository.save(
                        new Student(
                                "Nguyễn Văn An",
                                "an@fpt.edu.vn",
                                20,
                                cntt,
                                3.5
                        )
                );

                studentRepository.save(
                        new Student(
                                "Trần Thị Bình",
                                "binh@fpt.edu.vn",
                                21,
                                ktpm,
                                3.2
                        )
                );

                studentRepository.save(
                        new Student(
                                "Lê Minh Cường",
                                "cuong@fpt.edu.vn",
                                19,
                                attt,
                                3.8
                        )
                );

                studentRepository.save(
                        new Student(
                                "Phạm Thị Dung",
                                "dung@fpt.edu.vn",
                                22,
                                httt,
                                2.9
                        )
                );
            }
        };
    }
}