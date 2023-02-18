package com.challenge.quinto.impacto.services.implement;

import com.challenge.quinto.impacto.entities.CourseStudent;
import com.challenge.quinto.impacto.repositories.CourseStudentRepository;
import com.challenge.quinto.impacto.services.CourseStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseStudentServiceImplement implements CourseStudentService {

    @Autowired
    CourseStudentRepository courseStudentRepository;

    @Override
    public void saveCourseStudent(CourseStudent courseStudent) {
        courseStudentRepository.save(courseStudent);
    }
}
