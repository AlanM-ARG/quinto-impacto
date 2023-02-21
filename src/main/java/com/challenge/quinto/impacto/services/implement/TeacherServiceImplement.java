package com.challenge.quinto.impacto.services.implement;

import com.challenge.quinto.impacto.dtos.TeacherDTO;
import com.challenge.quinto.impacto.entities.Student;
import com.challenge.quinto.impacto.entities.Teacher;
import com.challenge.quinto.impacto.repositories.TeacherRepository;
import com.challenge.quinto.impacto.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImplement implements TeacherService {

    @Autowired
    TeacherRepository teacherRepository;


    @Override
    public void saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Override
    public Set<TeacherDTO> getAllTeachersDTO() {
        return teacherRepository.findAll().stream().map(TeacherDTO::new).collect(Collectors.toSet());
    }

    @Override
    public Set<Teacher> getAllTeachers() {
        return new HashSet<>(teacherRepository.findAll());
    }

    @Override
    public TeacherDTO findTeacherDTOById(Long id) {
        return teacherRepository.findById(id).map(TeacherDTO::new).orElse(null);
    }

    @Override
    public Teacher findTeacherById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @Override
    public TeacherDTO findTeacherDTOByEmail(String email) {
        return teacherRepository.findByEmail(email).map(TeacherDTO::new).orElse(null);
    }

    @Override
    public Teacher findTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Set<String> getAllTokens() {
        return teacherRepository.findAll().stream().map(Teacher::getToken).collect(Collectors.toSet());
    }

    @Override
    public Teacher findByToken(String token) {
        return teacherRepository.findByToken(token).orElse(null);
    }

}
