package com.studentservice.impl;

import com.studentservice.entities.Marks;
import com.studentservice.entities.Student;
import com.studentservice.entities.Subject;
import com.studentservice.repository.StudentRepository;
import com.studentservice.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        //get the marks of the student through API of marksService
        Marks[] marksOfUser = restTemplate.getForObject("http://localhost:8082/marks/student/"+student.getUserId(), Marks[].class);
        logger.info("{} ",marksOfUser);

        List<Marks> marksl=Arrays.stream(marksOfUser).toList();

        List<Marks> marksList= marksl.stream().map(marks -> {

            //api call to exam service to get the subject
            ResponseEntity<Subject> forEntity=restTemplate.getForEntity("http://localhost:8081/subject/"+marks.getSubjectId(), Subject.class);
            Subject subject=forEntity.getBody();
            logger.info("response status code: {}",forEntity.getStatusCode());

            //set the subject of the marks
            marks.setSubject(subject);

            return marks;
        }).collect(Collectors.toList());


        //set the marks of the student
        //Important= main baat ye hai ki hum doosre microservice se data kese fetch kr rhe hai vo seekhna hai ab us data
        // k akya krna hai voh tum dekhlo agar tum ni chahte ki student ke saath uske marks dikhe toh simply student class mai
        // jaao and vaha se Marks ki property hata do. And yaha se data fetch kro and bina student.addMarks()
        // kre direct jaha dikhana hai data vaha dikhado.
        //similarly agar tum nhi chahte ki subject marks ke saath dikhe toh isi project ke Marks class mai se Subject
        // hata do and marks.setSubject() bhi hata do and data tumhe yaha phir bhi milega bas tumne use set nhi kra
        // student ans marks mai toh ab us data ka jo krna hai vo kro
        student.setMarks(marksList);

        System.out.println("about to return student");



        return student;
    }
}
