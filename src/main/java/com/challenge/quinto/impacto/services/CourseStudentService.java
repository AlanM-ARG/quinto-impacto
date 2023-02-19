package com.challenge.quinto.impacto.services;

import com.challenge.quinto.impacto.dtos.CourseDTO;
import com.challenge.quinto.impacto.dtos.CourseStudentDTO;
import com.challenge.quinto.impacto.entities.CourseStudent;

import java.util.Set;

public interface CourseStudentService {

    void saveCourseStudent(CourseStudent courseStudent);

    Set<CourseStudentDTO> getAllCoursesStudentsDTO();

    Set<CourseStudent> getAllCoursesStudents();

    CourseStudentDTO findCourseStudentDTOById(Long id);

    CourseStudent findCourseStudentById(Long id);

    CourseStudentDTO findCourseStudentDTOByTitle(String title);

    CourseStudent findCourseStudentByTitle(String title);


}
