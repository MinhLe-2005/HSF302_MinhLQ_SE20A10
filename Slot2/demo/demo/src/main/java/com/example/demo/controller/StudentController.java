package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // GET: Lấy tất cả students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // POST: Thêm student mới
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        studentService.createStudent(
            student.getFullName(), 
            student.getEmail(), 
            student.getAge()
        );
        return student;
    }

    // PUT: Cập nhật student
    @PutMapping("/{id}")
    public String updateStudent(@PathVariable Long id, @RequestBody Student student) {
        studentService.updateStudent(
            id,
            student.getFullName(),
            student.getEmail(),
            student.getAge()
        );
        return "Student updated successfully";
    }

    // DELETE: Xóa student
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "Student deleted successfully";
    }
}
