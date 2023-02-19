package com.challenge.quinto.impacto.controllers;

import com.challenge.quinto.impacto.dtos.CourseDTO;
import com.challenge.quinto.impacto.entities.Course;
import com.challenge.quinto.impacto.services.CourseService;
import com.challenge.quinto.impacto.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    TeacherService teacherService;

    @GetMapping("/courses")
    public Set<CourseDTO> getAllCoursesDTO(){
        return courseService.getAllCoursesDTO();
    }

    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@RequestParam String title,@RequestParam String description,@RequestParam String coverPage){

// cambiar el dto por entidad
        CourseDTO courseDTO = courseService.findCourseDTOByTitle(title);

        if (title.isEmpty()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (description.isEmpty()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (coverPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (courseDTO != null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        courseService.saveCourse(new Course(title, description, coverPage));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/courses/teacher")
    public ResponseEntity<?> changeCourseTeacher (@RequestParam Long courseID, @RequestParam Long teacherID){

// cambiar el dto por entidad

        CourseDTO courseDTO = courseService.findCourseDTOById(courseID);


        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
