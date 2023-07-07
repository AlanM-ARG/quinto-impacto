package com.challenge.quinto.impacto.controllers;

import com.challenge.quinto.impacto.dtos.CourseDTO;
import com.challenge.quinto.impacto.services.CourseService;
import com.challenge.quinto.impacto.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;


    @GetMapping("/courses/{id}")
    public CourseDTO getCourseDTO(@PathVariable Long id){
        return courseService.findCourseDTOById(id);
    }

    @GetMapping("/courses")
    public Set<CourseDTO> getAllCoursesDTO(){
        return courseService.getAllCoursesDTO();
    }

    @GetMapping("/courses/active")
    public Set<CourseDTO> getAllCoursesDTOActives(){
        return courseService.getCoursesActives().stream().map(CourseDTO::new).collect(Collectors.toSet());
    }

    @GetMapping("/courses/current")
    public Set<CourseDTO> getCoursesCurrentDTO(Authentication authentication){
        return studentService.findStudentByEmail(authentication.getName()).getCourseStudents().stream().map(courseStudent -> new CourseDTO(courseStudent.getCourse())).collect(Collectors.toSet());
    }

    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@RequestParam String title,@RequestParam String description,@RequestParam String coverPage, @RequestParam String shifts, @RequestParam String category){

        return courseService.createCourseBody(title,description,shifts,category,coverPage);

    }

    @PatchMapping("/courses/teacher")
    public ResponseEntity<?> changeCourseTeacher (@RequestParam Long courseID, @RequestParam Long teacherID){

        return courseService.changeCourseTeacherBody(courseID, teacherID);
    }

    @PatchMapping("/courses/teacher/delete")
    public ResponseEntity<?> deleteCourseTeacher(@RequestParam Long courseID){

        return courseService.deleteCourseTeacherBody(courseID);

    }

    @PatchMapping("/course/disable/{id}")
    public ResponseEntity<?> disableCourse(@PathVariable Long id) {

            return courseService.disableCourseBody(id);

    }

}
