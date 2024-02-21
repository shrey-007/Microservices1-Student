package com.studentservice.repository;

import com.studentservice.entities.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,String> {

}
