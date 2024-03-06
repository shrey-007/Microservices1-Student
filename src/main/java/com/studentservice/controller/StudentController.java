package com.studentservice.controller;

import com.studentservice.entities.Student;
import com.studentservice.impl.StudentServiceImpl;
import com.studentservice.service.StudentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger= LoggerFactory.getLogger(StudentController.class);

    @PostMapping
    public ResponseEntity<Student> createUser(@RequestBody Student student){

        Student savedStudent=studentService.saveStudent(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @GetMapping("/{userId}")
    @CircuitBreaker(name = "circuitBreaker",fallbackMethod = "fallback")
    //if any of the service exam, marks is not working then fallback method will be called
    public ResponseEntity<Student> getSingleStudent(@PathVariable String userId){
        Student student=studentService.getStudent(userId);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity <List<Student>> getAllStudents(){

        List<Student> students=studentService.getAllStudents();

        return ResponseEntity.ok(students);
    }

    //creating fallback method for circuitBreaker
    public ResponseEntity<Student> fallback(String userId,Exception e){
        logger.info("Fallback is executed because service is down : ",e.getMessage());
        Student student=Student.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is created dummy because some service is down")
                .userId("Dummy")
                .build();
        return new ResponseEntity<>(student,HttpStatus.OK);

    }


}
