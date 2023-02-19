package com.challenge.quinto.impacto.services;

import com.challenge.quinto.impacto.dtos.CourseDTO;
import com.challenge.quinto.impacto.dtos.StudentDTO;
import com.challenge.quinto.impacto.entities.Student;

import java.util.Set;

public interface StudentService {

    void saveStudent(Student student);

    Set<StudentDTO> getAllStudentDTO();

    Set<Student> getAllStudent();

    StudentDTO findStudentDTOById(Long id);

    Student findStudentById(Long id);

    StudentDTO findStudentDTOByEmail(String email);

    Student findStudentByEmail(String email);

}
