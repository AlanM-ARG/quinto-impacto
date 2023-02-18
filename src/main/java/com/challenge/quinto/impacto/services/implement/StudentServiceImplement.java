package com.challenge.quinto.impacto.services.implement;

import com.challenge.quinto.impacto.entities.Student;
import com.challenge.quinto.impacto.repositories.StudentRepository;
import com.challenge.quinto.impacto.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImplement implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }
}
