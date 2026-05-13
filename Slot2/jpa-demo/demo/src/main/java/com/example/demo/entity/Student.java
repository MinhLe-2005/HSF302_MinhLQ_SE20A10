package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(unique = true)
    private String email;

    private Integer age;

    // Constructor, getter, setter, toString...
    public Student() {}

    public Student(String fullName, String email, Integer age) {
        this.fullName = fullName;
        this.email = email;
        this.age = age;
    }

    // getter/setter omitted for brevity
}
