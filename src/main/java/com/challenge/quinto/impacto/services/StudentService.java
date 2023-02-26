package com.challenge.quinto.impacto.services;

import com.challenge.quinto.impacto.dtos.StudentDTO;
import com.challenge.quinto.impacto.entities.Student;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public interface StudentService {

    void saveStudent(Student student);

    Set<StudentDTO> getAllStudentDTO();

    Set<Student> getAllStudent();

    StudentDTO findStudentDTOById(Long id);

    Student findStudentById(Long id);

    StudentDTO findStudentDTOByEmail(String email);

    Student findStudentByEmail(String email);

    Set<String> getAllTokens ();

    Student findByToken(String token);

    ResponseEntity<?> registerStudentBody(String email, String firstName, String lastName, String password);

    ResponseEntity<?> confirmStudentBody(String token, HttpServletResponse response) throws IOException;

    ResponseEntity<?> changePasswordBody(String email, String password, String oldPassword);

    ResponseEntity<?> saveImageBody(String email, String image);

}
