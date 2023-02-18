package com.challenge.quinto.impacto.services;

import com.challenge.quinto.impacto.dtos.CourseDTO;
import com.challenge.quinto.impacto.entities.Course;

import java.util.List;
import java.util.Set;

public interface CourseService {

    void saveCourse(Course course);

    Set<CourseDTO> getAllCoursesDTO();

    CourseDTO findCourseDTOById(Long id);

    CourseDTO findCourseDTOByTitle(String title);

}
