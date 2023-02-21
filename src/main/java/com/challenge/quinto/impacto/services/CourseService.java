package com.challenge.quinto.impacto.services;

import com.challenge.quinto.impacto.dtos.CourseDTO;
import com.challenge.quinto.impacto.entities.Course;
import com.challenge.quinto.impacto.entities.CourseStudent;

import java.util.List;
import java.util.Set;

public interface CourseService {

    void saveCourse(Course course);

    Set<CourseDTO> getAllCoursesDTO();

    Set<Course> getAllCourses();

    CourseDTO findCourseDTOById(Long id);

    Course findCourseById(Long id);

    CourseDTO findCourseDTOByTitle(String title);

    Course findCourseByTitle(String title);
    void deleteStudentCourse(Course course, CourseStudent courseStudent);

}
