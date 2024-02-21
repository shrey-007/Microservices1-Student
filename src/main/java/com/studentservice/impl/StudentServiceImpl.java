package com.studentservice.impl;

import com.studentservice.entities.Student;
import com.studentservice.repository.StudentRepository;
import com.studentservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return studentRepository.getById(userId);
    }

}
