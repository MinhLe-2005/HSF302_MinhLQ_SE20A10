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
class DemoApplicationTests {

	@Autowired
	private StudentService studentService;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	void testCreateStudent() {
		// Create a student with unique email
		studentService.createStudent("Test User", "test@example.com", 20);

		// Find the student - get last created
		var students = studentService.getAllStudents();
		Student student = students.get(students.size() - 1);

		// Verify
		assertNotNull(student);
		assertEquals("Test User", student.getFullName());
		assertEquals("test@example.com", student.getEmail());
		assertEquals(20, student.getAge());
	}

	@Test
	void testGetAllStudents() {
		// Create students with unique emails
		studentService.createStudent("User X", "x@example.com", 20);
		studentService.createStudent("User Y", "y@example.com", 21);

		// Get all students
		var students = studentService.getAllStudents();

		// Verify - at least 2 students exist
		assertTrue(students.size() >= 2);
	}
}
