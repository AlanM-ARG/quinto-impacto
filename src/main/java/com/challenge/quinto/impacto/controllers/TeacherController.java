package com.challenge.quinto.impacto.controllers;

import com.challenge.quinto.impacto.dtos.CourseDTO;
import com.challenge.quinto.impacto.dtos.TeacherDTO;
import com.challenge.quinto.impacto.entities.Course;
import com.challenge.quinto.impacto.entities.Student;
import com.challenge.quinto.impacto.entities.Teacher;
import com.challenge.quinto.impacto.services.CourseService;
import com.challenge.quinto.impacto.services.EmailService;
import com.challenge.quinto.impacto.services.StudentService;
import com.challenge.quinto.impacto.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/teacher/{id}")
    public TeacherDTO getTeacherDTO(@PathVariable Long id){
        return teacherService.findTeacherDTOById(id);
    }

    @GetMapping("/teacher")
    public Set<TeacherDTO> getAllTeachersDTO(){
        return teacherService.getAllTeachersDTO();
    }

    @GetMapping("/teacher/current")
    public TeacherDTO getTeacherCurrentDTO(Authentication authentication){
        return teacherService.findTeacherDTOByEmail(authentication.getName());
    }

    @PostMapping("/teacher")
    public ResponseEntity<?> registerTeacher( @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password){

        Student student = studentService.findStudentByEmail(email);

        Teacher teacher = teacherService.findTeacherByEmail(email);

        if (firstName.isEmpty() ){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (lastName.isEmpty()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (email.isEmpty() ){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (password.isEmpty()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (student != null && teacher != null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Set<String> allTokens = teacherService.getAllTokens();

        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (allTokens.contains(token));

        Teacher newTeacher = new Teacher(firstName, lastName, email, passwordEncoder.encode(password),"https://i.ibb.co/cFr5syK/educacion-en-el-hogar.png", token);

        teacherService.saveTeacher(newTeacher);

        String link = "http://localhost:8080/api/teacher/confirm/" + token;

        emailService.sendEmail(newTeacher.getEmail(), emailService.buildEmail(newTeacher.getFirstName(), link));

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/teacher/confirm/{token}")
    public ResponseEntity<?> confirmTeacher (@PathVariable String token, HttpServletResponse response) throws IOException {

        if (token.isEmpty()){
            return new ResponseEntity<>("Token cannot be found", HttpStatus.FORBIDDEN);
        }

        Teacher teacher = teacherService.findByToken(token);

        if (teacher != null) {


            if (teacher.getActive()) {
                return new ResponseEntity<>("The teacher is already activated", HttpStatus.FORBIDDEN);
            }

            teacher.setActive(true);

            teacherService.saveTeacher(teacher);

            response.sendRedirect("/web/login-register.html?confirmed=true");

            return new ResponseEntity<>("Teacher Confirmed", HttpStatus.OK);

        }
        return new ResponseEntity<>("No Teacher with this token was found.", HttpStatus.FORBIDDEN);

    }

    @PatchMapping("/teacher/courses/delete")
    public ResponseEntity<?> deleteCourseTeacher(@RequestParam Long teacherID, @RequestParam Long courseID){

        Teacher teacher = teacherService.findTeacherById(teacherID);

        Course course = courseService.findCourseById(courseID);

        if (!teacher.getCourses().contains(course)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (course == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (course.getTeacher() == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        course.setTeacher(null);

        courseService.saveCourse(course);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
