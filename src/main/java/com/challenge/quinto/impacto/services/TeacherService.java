package com.challenge.quinto.impacto.services;

import com.challenge.quinto.impacto.dtos.TeacherDTO;
import com.challenge.quinto.impacto.entities.Teacher;

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


}
