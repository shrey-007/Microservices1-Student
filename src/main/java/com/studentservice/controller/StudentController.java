package com.studentservice.controller;

import com.studentservice.entities.Student;
import com.studentservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @PostMapping
    public ResponseEntity<Student> createUser(@RequestBody Student student){

        Student savedStudent=studentService.saveStudent(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Student> getSingleStudent(@PathVariable String userId){
        Student student=studentService.getStudent(userId);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity <List<Student>> getAllStudents(){

        List<Student> students=studentService.getAllStudents();

        return ResponseEntity.ok(students);
    }

}
