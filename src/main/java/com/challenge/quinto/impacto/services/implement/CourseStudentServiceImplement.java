package com.challenge.quinto.impacto.services.implement;

import com.challenge.quinto.impacto.dtos.CourseDTO;
import com.challenge.quinto.impacto.dtos.CourseStudentDTO;
import com.challenge.quinto.impacto.entities.CourseStudent;
import com.challenge.quinto.impacto.repositories.CourseStudentRepository;
import com.challenge.quinto.impacto.services.CourseStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseStudentServiceImplement implements CourseStudentService {

    @Autowired
    CourseStudentRepository courseStudentRepository;

    @Override
    public void saveCourseStudent(CourseStudent courseStudent) {
        courseStudentRepository.save(courseStudent);
    }

    @Override
    public Set<CourseStudentDTO> getAllCoursesStudentsDTO() {
        return courseStudentRepository.findAll().stream().map(CourseStudentDTO::new).collect(Collectors.toSet());
    }

    @Override
    public CourseStudentDTO findCourseStudentDTOById(Long id) {
        return courseStudentRepository.findById(id).map(CourseStudentDTO::new).orElse(null);
    }

    @Override
    public CourseStudentDTO findCourseStudentDTOByTitle(String title) {
        return courseStudentRepository.findByTitle(title).map(CourseStudentDTO::new).orElse(null);
    }

}
