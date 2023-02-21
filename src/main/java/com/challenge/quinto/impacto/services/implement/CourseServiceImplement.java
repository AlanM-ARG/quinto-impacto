package com.challenge.quinto.impacto.services.implement;

import com.challenge.quinto.impacto.dtos.CourseDTO;
import com.challenge.quinto.impacto.entities.Course;
import com.challenge.quinto.impacto.entities.CourseStudent;
import com.challenge.quinto.impacto.repositories.CourseRepository;
import com.challenge.quinto.impacto.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImplement implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Override
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public Set<CourseDTO> getAllCoursesDTO() {
        return courseRepository.findAll().stream().map(CourseDTO::new).collect(Collectors.toSet());
    }

    @Override
    public Set<Course> getAllCourses() {
        return new HashSet<>(courseRepository.findAll());
    }

    @Override
    public CourseDTO findCourseDTOById(Long id) {
        return courseRepository.findById(id).map(CourseDTO::new).orElse(null);
    }

    @Override
    public Course findCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public CourseDTO findCourseDTOByTitle(String title) {
        return courseRepository.findByTitle(title).map(CourseDTO::new).orElse(null);
    }

    @Override
    public Course findCourseByTitle(String title) {
        return courseRepository.findByTitle(title).orElse(null);
    }

    @Override
    public void deleteStudentCourse(Course course, CourseStudent courseStudent) {
        course.getCourseStudents().remove(courseStudent);
        courseRepository.save(course);
    }

}
