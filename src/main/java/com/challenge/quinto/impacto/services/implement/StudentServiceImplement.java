package com.challenge.quinto.impacto.services.implement;

import com.challenge.quinto.impacto.dtos.StudentDTO;
import com.challenge.quinto.impacto.entities.Student;
import com.challenge.quinto.impacto.repositories.StudentRepository;
import com.challenge.quinto.impacto.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImplement implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Set<StudentDTO> getAllStudentDTO() {
        return studentRepository.findAll().stream().map(StudentDTO::new).collect(Collectors.toSet());
    }

    @Override
    public StudentDTO findStudentDTOById(Long id) {
        return studentRepository.findById(id).map(StudentDTO::new).orElse(null);
    }

    @Override
    public StudentDTO findStudentDTOByEmail(String email) {
        return studentRepository.findByEmail(email).map(StudentDTO::new).orElse(null);
    }
}
