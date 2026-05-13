package com.example.demo;

import com.example.demo.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(StudentService studentService) {
        return args -> {
            System.out.println("\n === DEMO ĐỌC DỮ LIỆU (INSERT) ===");
            System.out.println("' HSP302 - DEMO ĐỌC DỮ LIỆU (H2 In Memory)");
            System.out.println("==".repeat(60));

            // === 1. THÊM DỮ LIỆU mới (createStudent)...\n");
            System.out.println("\n==> 1. Thêm dữ liệu mới (createStudent)...\n");

            studentService.createStudent("Trần Văn Thuận", "thuantvfpt@fpt.edu.vn", 20);
            studentService.createStudent("Nguyễn Văn An", "annv@fpt.edu.vn", 21);
            studentService.createStudent("Lê Thị Bình", "binhlt@fpt.edu.vn", 19);
            studentService.createStudent("Phạm Văn Cường", "cuongpv@fpt.edu.vn", 22);
            studentService.createStudent("Hoàng Thị Dung", "dunghtd@fpt.edu.vn", 20);
        };
    }
}
