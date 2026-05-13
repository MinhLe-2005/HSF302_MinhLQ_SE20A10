package com.example.demo.service;

import com.example.demo.entity.Student;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void createStudent(String name, String email, int age) {
        Student s = new Student(name, email, age);
        em.persist(s);  // INSERT
        System.out.println("Saved with ID = " + s.getId());
    }

    @Transactional(readOnly = true)
    public void printAll() {
        em.createQuery("SELECT s FROM Student s", Student.class)
                .getResultList()
                .forEach(System.out::println);
    }

    @Transactional(readOnly = true)
    public java.util.List<Student> getAllStudents() {
        return em.createQuery("SELECT s FROM Student s", Student.class)
                .getResultList();
    }

    @Transactional
    public void updateStudent(Long id, String name, String email, int age) {
        Student s = em.find(Student.class, id);
        if (s != null) {
            s.setFullName(name);
            s.setEmail(email);
            s.setAge(age);
            em.merge(s);  // UPDATE
            System.out.println("Updated student with ID = " + id);
        }
    }

    @Transactional
    public void deleteStudent(Long id) {
        Student s = em.find(Student.class, id);
        if (s != null) {
            em.remove(s);  // DELETE
            System.out.println("Deleted student with ID = " + id);
        }
    }
}
