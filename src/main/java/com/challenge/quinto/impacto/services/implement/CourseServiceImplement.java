package com.challenge.quinto.impacto.services.implement;

import com.challenge.quinto.impacto.entities.Course;
import com.challenge.quinto.impacto.repositories.CourseRepository;
import com.challenge.quinto.impacto.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImplement implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Override
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

}
