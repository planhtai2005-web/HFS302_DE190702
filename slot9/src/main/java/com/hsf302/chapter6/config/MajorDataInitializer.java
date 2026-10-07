package com.hsf302.chapter6.config;

import com.hsf302.chapter6.entity.Major;
import com.hsf302.chapter6.repository.MajorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MajorDataInitializer implements CommandLineRunner {

    private final MajorRepository majorRepository;

    public MajorDataInitializer(MajorRepository majorRepository) {
        this.majorRepository = majorRepository;
    }

    @Override
    public void run(String... args) {

        createIfNotExists(
                "CNTT",
                "Công nghệ thông tin"
        );

        createIfNotExists(
                "KTPM",
                "Kỹ thuật phần mềm"
        );

        createIfNotExists(
                "HTTT",
                "Hệ thống thông tin"
        );

        createIfNotExists(
                "ATTT",
                "An toàn thông tin"
        );

        createIfNotExists(
                "MMT",
                "Mạng máy tính"
        );
    }

    private void createIfNotExists(
            String code,
            String name) {

        if (!majorRepository.existsByCodeIgnoreCase(code)) {

            majorRepository.save(
                    new Major(code, name)
            );
        }
    }
}