package com.studentservice.service;

import com.studentservice.entities.Student;

import java.util.List;

public interface StudentService {
    Student saveStudent(Student student);

    List<Student> getAllStudents();

    Student getStudent(String userId);
}
