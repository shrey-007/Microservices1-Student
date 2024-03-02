package com.studentservice.impl;

import com.studentservice.entities.Marks;
import com.studentservice.entities.Student;
import com.studentservice.repository.StudentRepository;
import com.studentservice.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
/* Important thing is ki service class mai @Service nhi lagaya, yaha lagaya hai
   Toh dekho @Service ka 2 faayde hai
   1) annotations like @Autowire scan ho jaaega toh service class mai koi @Autowire tha hi nhi isliye vaha nhi daala
   2) Project mai pata pade ki konsi class kya kr rhi hai, toh ye class bhi toh service hi de rhi hai toh isme laga diya @Service
*/
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    //ye logger and restTemplate ka use doosri  microservice se interact krne ke liye hai
    @Autowired
    private RestTemplate restTemplate;
    //since ye autowire hai toh iska bean kahi hona chaiye toh iska bean bana do configuration class mai using @Bean
    // annotation. SpringBoot ki main class khud ek configuration class hi hoti hai toh usme mai kr skte hai  alag se
    // config package banane ki need nhi hai. But config package mai hi daali hai


    private Logger logger= LoggerFactory.getLogger(StudentServiceImpl.class);
    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudent(String userId) {
        //get the student from database, but this database does contains marks of the student.

        Student student=studentRepository.getByUserId(userId);
        System.out.println("impl");
        //get the marks of the student through API of marksService
        ArrayList<Marks> marksOfUser = restTemplate.getForObject("http://localhost:8082/marks/student/"+student.getUserId(), ArrayList.class);
        logger.info("{} ",marksOfUser);

        student.setMarks(marksOfUser);

        System.out.println("about to return student");



        return student;
    }
}
