package com.challenge.quinto.impacto.controllers;

import com.challenge.quinto.impacto.dtos.CourseStudentDTO;
import com.challenge.quinto.impacto.services.CourseStudentService;
import com.challenge.quinto.impacto.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CourseStudentController {

    @Autowired
    CourseStudentService courseStudentService;

    @Autowired
    StudentService studentService;


    @GetMapping("/courseStudent/{id}")
    public CourseStudentDTO getCourseStudentDTO(@PathVariable Long id){
        return courseStudentService.findCourseStudentDTOById(id);
    }

    @GetMapping("/courseStudent")
    public Set<CourseStudentDTO> getAllCourseStudentDTO(){
        return courseStudentService.getAllCoursesStudentsDTO();
    }

    @GetMapping("/courseStudent/current")
    public Set<CourseStudentDTO> getCourseStudentCurrentDTO(Authentication authentication) {
        return studentService.findStudentByEmail(authentication.getName()).getCourseStudents().stream().map(CourseStudentDTO::new).collect(Collectors.toSet());
    }

    @PostMapping("/courseStudent")
    public ResponseEntity<?> createCourseStudent(@RequestParam Long courseID, Authentication authentication){

        return courseStudentService.createCourseStudentBody(authentication.getName(), courseID);

    }

}
