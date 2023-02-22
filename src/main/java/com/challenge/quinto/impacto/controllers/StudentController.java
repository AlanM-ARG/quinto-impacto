package com.challenge.quinto.impacto.controllers;

import com.challenge.quinto.impacto.dtos.StudentDTO;
import com.challenge.quinto.impacto.dtos.TeacherDTO;
import com.challenge.quinto.impacto.entities.Course;
import com.challenge.quinto.impacto.entities.Student;
import com.challenge.quinto.impacto.entities.Teacher;
import com.challenge.quinto.impacto.services.*;
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

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    CourseStudentService courseStudentService;

    @Autowired
    CourseService courseService;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/students/{id}")
    public StudentDTO getStudentDTO(@PathVariable Long id){
        return studentService.findStudentDTOById(id);
    }

    @GetMapping("/students")
    public Set<StudentDTO> getAllStudentsDTO(){
        return studentService.getAllStudentDTO();
    }

    @GetMapping("/students/current")
    public StudentDTO getStudentCurrentDTO(Authentication authentication){
        return studentService.findStudentDTOByEmail(authentication.getName());
    }

    @PostMapping("/students")
    public ResponseEntity<?> registerStudent(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password){

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

        Set<String> allTokens = studentService.getAllTokens();

        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (allTokens.contains(token));

        Student newStudent = new Student(firstName, lastName, email, passwordEncoder.encode(password),"https://i.ibb.co/yFKrNwB/estudio.png", token);

        studentService.saveStudent(newStudent);

        String link = "http://localhost:8080/api/students/confirm/" + token;

        emailService.sendEmail(newStudent.getEmail(), emailService.buildEmail(newStudent.getFirstName(), link));

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/students/confirm/{token}")
    public ResponseEntity<?> confirmStudent (@PathVariable String token, HttpServletResponse response) throws IOException {

        if (token.isEmpty()){
            return new ResponseEntity<>("Token cannot be found", HttpStatus.FORBIDDEN);
        }

        Student student = studentService.findByToken(token);

        if (student != null) {


            if (student.getActive()) {
                return new ResponseEntity<>("The student is already activated", HttpStatus.FORBIDDEN);
            }

            student.setActive(true);

            studentService.saveStudent(student);

            response.sendRedirect("/web/login-register.html?confirmed=true");

            return new ResponseEntity<>("Student Confirmed", HttpStatus.OK);

        }
        return new ResponseEntity<>("No Student with this token was found.", HttpStatus.FORBIDDEN);

    }


}
