package com.challenge.quinto.impacto.services;

import com.challenge.quinto.impacto.dtos.CourseDTO;
import com.challenge.quinto.impacto.dtos.TeacherDTO;
import com.challenge.quinto.impacto.entities.Teacher;

import java.util.Set;

public interface TeacherService {

    void saveTeacher(Teacher teacher);

    Set<TeacherDTO> getAllTeachersDTO();

    TeacherDTO findTeacherDTOById(Long id);

    TeacherDTO findTeacherDTOByEmail(String email);

}
