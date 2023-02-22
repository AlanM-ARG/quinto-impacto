package com.challenge.quinto.impacto.controllers;

import com.challenge.quinto.impacto.dtos.CourseDTO;
import com.challenge.quinto.impacto.dtos.CourseStudentDTO;
import com.challenge.quinto.impacto.entities.Course;
import com.challenge.quinto.impacto.entities.CourseStudent;
import com.challenge.quinto.impacto.entities.Student;
import com.challenge.quinto.impacto.services.CourseService;
import com.challenge.quinto.impacto.services.CourseStudentService;
import com.challenge.quinto.impacto.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CourseStudentController {

    @Autowired
    CourseStudentService courseStudentService;

    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

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

        Student student = studentService.findStudentByEmail(authentication.getName());

        if(student != null){

            Set<Course> coursesStudent = student.getCourseStudents().stream().map(CourseStudent::getCourse).collect(Collectors.toSet());

            Course course = courseService.findCourseById(courseID);

            if (coursesStudent.contains(course)){
                return new ResponseEntity<>("El estudiante ya se encuentra inscripto a este curso", HttpStatus.FORBIDDEN);
            }

            CourseStudent courseStudent = new CourseStudent(course, student);

            courseStudentService.saveCourseStudent(courseStudent);

            return new ResponseEntity<>("Inscripto correctamente",HttpStatus.OK);

        }

        return new ResponseEntity<>("Debes estas autenticado para inscribirte a un curso",HttpStatus.FORBIDDEN);

    }

}
