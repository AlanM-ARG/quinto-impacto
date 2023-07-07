package com.challenge.quinto.impacto.services;

import com.challenge.quinto.impacto.dtos.TeacherDTO;
import com.challenge.quinto.impacto.entities.Teacher;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public interface TeacherService {

    void saveTeacher(Teacher teacher);

    Set<TeacherDTO> getAllTeachersDTO();

    Set<Teacher> getAllTeachers();

    TeacherDTO findTeacherDTOById(Long id);

    Teacher findTeacherById(Long id);

    TeacherDTO findTeacherDTOByEmail(String email);

    Teacher findTeacherByEmail(String email);

    Set<String> getAllTokens ();

    Teacher findByToken(String token);

    ResponseEntity<?> changePasswordBody(String email, String password, String oldPassword);

    ResponseEntity<?> registerTeacherBody(String email, String firstName, String lastName, String password);

    ResponseEntity<?> confirmTeacherBody(String token, HttpServletResponse response) throws IOException;

    ResponseEntity<?> deleteCourseTeacherBody(Long teacherID, Long courseID);

    ResponseEntity<?> saveImageBody(String image, String email);


}
