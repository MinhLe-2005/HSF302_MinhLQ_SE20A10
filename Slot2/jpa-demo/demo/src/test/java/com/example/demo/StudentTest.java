package com.example.demo;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.main.lazy-initialization=true")
@Transactional
public class StudentTest {

    @Autowired
    private StudentService studentService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testCreateStudent() {
        // Create a student first with unique email
        studentService.createStudent("Nguyễn Văn A", "nguyenvana@example.com", 20);
        
        // Flush to ensure it's saved
        entityManager.flush();
        entityManager.clear();

        // Get the created student ID
        var students = studentService.getAllStudents();
        Long studentId = students.get(students.size() - 1).getId();

        // Show student BEFORE update
        System.out.println("\n=== THÔNG TIN SINH VIÊN TRƯỚC KHI UPDATE ===");
        Student studentBefore = entityManager.find(Student.class, studentId);
        System.out.println("ID: " + studentBefore.getId());
        System.out.println("Họ tên: " + studentBefore.getFullName());
        System.out.println("Email: " + studentBefore.getEmail());
        System.out.println("Tuổi: " + studentBefore.getAge());

        // Update the student
        System.out.println("\n>>> Đang cập nhật sinh viên ID: " + studentId);
        studentService.updateStudent(studentId, "Nguyễn Văn A Updated", "nguyenvana.updated@example.com", 25);
        
        // Flush again
        entityManager.flush();
        entityManager.clear();

        // Show student AFTER update
        System.out.println("\n=== THÔNG TIN SINH VIÊN SAU KHI UPDATE ===");
        Student studentAfter = entityManager.find(Student.class, studentId);
        System.out.println("ID: " + studentAfter.getId());
        System.out.println("Họ tên: " + studentAfter.getFullName());
        System.out.println("Email: " + studentAfter.getEmail());
        System.out.println("Tuổi: " + studentAfter.getAge());

        // Verify
        assertNotNull(studentAfter);
        assertEquals("Nguyễn Văn A Updated", studentAfter.getFullName());
        assertEquals("nguyenvana.updated@example.com", studentAfter.getEmail());
        assertEquals(25, studentAfter.getAge());
        
        System.out.println("\n✅ Test UPDATE thành công!");
    }

    @Test
    public void testCreateAndRetrieveStudentFromDatabase() {
        // Tạo student mới
        studentService.createStudent("Test Student", "test@fpt.edu.vn", 25);
        entityManager.flush();
        entityManager.clear();

        // Lấy ID của student vừa tạo
        var students = studentService.getAllStudents();
        Long createdStudentId = students.get(students.size() - 1).getId();

        // Lấy student từ database
        Student retrievedStudent = entityManager.find(Student.class, createdStudentId);

        // Verify student tồn tại
        assertNotNull(retrievedStudent, "Student should exist in database");
        
        // Verify từng field
        assertEquals(createdStudentId, retrievedStudent.getId());
        assertEquals("Test Student", retrievedStudent.getFullName());
        assertEquals("test@fpt.edu.vn", retrievedStudent.getEmail());
        assertEquals(25, retrievedStudent.getAge());
        
        System.out.println("\n✅ Test CREATE and RETRIEVE thành công!");
        System.out.println("Student ID: " + retrievedStudent.getId());
        System.out.println("Full Name: " + retrievedStudent.getFullName());
        System.out.println("Email: " + retrievedStudent.getEmail());
        System.out.println("Age: " + retrievedStudent.getAge());
    }

    @Test
    public void testDeleteStudent() {
        // Create some students
        studentService.createStudent("Student 1", "student1@example.com", 20);
        studentService.createStudent("Student 2", "student2@example.com", 21);
        studentService.createStudent("Student 3", "student3@example.com", 22);
        entityManager.flush();
        entityManager.clear();

        // Show list BEFORE deletion
        System.out.println("\n=== DANH SÁCH SINH VIÊN TRƯỚC KHI XÓA ===");
        var studentsBefore = studentService.getAllStudents();
        studentsBefore.forEach(s -> System.out.println("ID: " + s.getId() + " - " + s.getFullName() + " - " + s.getEmail()));
        int countBefore = studentsBefore.size();

        // Get ID of student to delete (middle one)
        Long studentIdToDelete = studentsBefore.get(studentsBefore.size() - 2).getId();
        System.out.println("\n>>> Đang xóa sinh viên ID: " + studentIdToDelete);

        // Delete the student
        studentService.deleteStudent(studentIdToDelete);
        entityManager.flush();
        entityManager.clear();

        // Show list AFTER deletion
        System.out.println("\n=== DANH SÁCH SINH VIÊN SAU KHI XÓA ===");
        var studentsAfter = studentService.getAllStudents();
        studentsAfter.forEach(s -> System.out.println("ID: " + s.getId() + " - " + s.getFullName() + " - " + s.getEmail()));
        int countAfter = studentsAfter.size();

        // Verify
        System.out.println("\nSố lượng trước: " + countBefore);
        System.out.println("Số lượng sau: " + countAfter);
        
        assertTrue(countAfter < countBefore, "Số lượng sinh viên phải giảm sau khi xóa");
        
        // Verify deleted student doesn't exist
        Student deletedStudent = entityManager.find(Student.class, studentIdToDelete);
        assertNull(deletedStudent, "Sinh viên đã xóa không còn tồn tại");
        
        System.out.println("\n✅ Test DELETE thành công!");
    }
}
