package com.challenge.quinto.impacto.services;

import com.challenge.quinto.impacto.dtos.CourseDTO;
import com.challenge.quinto.impacto.entities.Course;
import com.challenge.quinto.impacto.entities.CourseStudent;
import org.springframework.http.ResponseEntity;

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

    Set<Course> getCoursesActives();

    ResponseEntity<?> createCourseBody(String title, String description, String shifts, String category, String coverPage);

    ResponseEntity<?> changeCourseTeacherBody(Long courseID, Long teacherID);

    ResponseEntity<?> deleteCourseTeacherBody(Long courseID);


    ResponseEntity<?> disableCourseBody(Long id);
}
