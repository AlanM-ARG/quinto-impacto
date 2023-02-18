package com.challenge.quinto.impacto.services;

import com.challenge.quinto.impacto.dtos.CourseDTO;
import com.challenge.quinto.impacto.dtos.StudentDTO;
import com.challenge.quinto.impacto.entities.Student;

import java.util.Set;

public interface StudentService {

    void saveStudent(Student student);

    Set<StudentDTO> getAllStudentDTO();

    StudentDTO findStudentDTOById(Long id);

    StudentDTO findStudentDTOByEmail(String email);

}
