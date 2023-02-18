package com.challenge.quinto.impacto.services.implement;

import com.challenge.quinto.impacto.entities.Teacher;
import com.challenge.quinto.impacto.repositories.TeacherRepository;
import com.challenge.quinto.impacto.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImplement implements TeacherService {

    @Autowired
    TeacherRepository teacherRepository;


    @Override
    public void saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }
}
