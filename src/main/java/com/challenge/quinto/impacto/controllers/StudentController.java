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
            return new ResponseEntity<>("Ingrese un nombre",HttpStatus.FORBIDDEN);
        }
        if (lastName.isEmpty()){
            return new ResponseEntity<>("Ingrese un apellido",HttpStatus.FORBIDDEN);
        }
        if (email.isEmpty() ){
            return new ResponseEntity<>("Ingrese un correo electronico",HttpStatus.FORBIDDEN);
        }
        if (password.isEmpty()){
            return new ResponseEntity<>("Ingrese una contraseña",HttpStatus.FORBIDDEN);
        }
        if (student != null && teacher != null){
            return new ResponseEntity<>("Ya exite un usuario con ese correo electronico",HttpStatus.FORBIDDEN);
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

        return new ResponseEntity<>("Estudiante registrado con exito",HttpStatus.CREATED);

    }

    @GetMapping("/students/confirm/{token}")
    public ResponseEntity<?> confirmStudent (@PathVariable String token, HttpServletResponse response) throws IOException {

        if (token.isEmpty()){
            return new ResponseEntity<>("No puede enviar un token vacio", HttpStatus.FORBIDDEN);
        }

        Student student = studentService.findByToken(token);

        if (student != null) {


            if (student.getActive()) {
                return new ResponseEntity<>("El estudiante ya se encontraba activado", HttpStatus.FORBIDDEN);
            }

            student.setActive(true);

            studentService.saveStudent(student);

            response.sendRedirect("/web/login-register.html?confirmed=true");

            return new ResponseEntity<>("Correo electronico de estudiante confirmado", HttpStatus.OK);

        }
        return new ResponseEntity<>("No se encontro ningun estudiante con ese token", HttpStatus.FORBIDDEN);

    }

    @PatchMapping("/students/current/changePassword")
    public ResponseEntity<?> changePassword(Authentication authentication, @RequestParam String password, @RequestParam String oldPassword){

        Student student = studentService.findStudentByEmail(authentication.getName());

        if (student == null){
            return new ResponseEntity<>("Debes iniciar sesion", HttpStatus.FORBIDDEN);
        }
        if (password.isEmpty()){
            return new ResponseEntity<>("Ingrese la contraseña nueva",HttpStatus.FORBIDDEN);
        }
        if (password.equals(oldPassword)){
            return new ResponseEntity<>("Las contraseñas son iguales",HttpStatus.FORBIDDEN);
        }
        if (!passwordEncoder.matches(oldPassword, student.getPassword())){
            return new ResponseEntity<>("Contraseña erronea",HttpStatus.FORBIDDEN);
        }

        student.setPassword(passwordEncoder.encode(password));
        studentService.saveStudent(student);

        return new ResponseEntity<>("Contraseña cambiada",HttpStatus.OK);
    }

    @PostMapping("/student/current/uploadImage")
    public ResponseEntity<?> saveImage(@RequestParam String image,Authentication authentication) {

        Student student = studentService.findStudentByEmail(authentication.getName());

        if (student == null){
            return new ResponseEntity<>("Debes iniciar sesion para realizar esta operacion",HttpStatus.FORBIDDEN);
        }
        if (image.isEmpty()){
            return new ResponseEntity<>("Seleccione una imagen",HttpStatus.FORBIDDEN);
        }

        student.setProfileImage(image);
        studentService.saveStudent(student);

        return new ResponseEntity<>("Imagen de perfil Cambiada", HttpStatus.OK);
    }
}
