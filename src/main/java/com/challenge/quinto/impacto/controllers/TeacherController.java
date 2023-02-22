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

    @PatchMapping("/teacher/current/changePassword")
    public ResponseEntity<?> changePassword(Authentication authentication, @RequestParam String password, @RequestParam String oldPassword){

        Teacher teacher = teacherService.findTeacherByEmail(authentication.getName());

        if (teacher == null){
            return new ResponseEntity<>("Debes iniciar sesion", HttpStatus.FORBIDDEN);
        }
        if (password.isEmpty()){
            return new ResponseEntity<>("Ingrese la contraseña nueva",HttpStatus.FORBIDDEN);
        }
        if (password.equals(oldPassword)){
            return new ResponseEntity<>("Las contraseñas son iguales",HttpStatus.FORBIDDEN);
        }
        if (!passwordEncoder.matches(oldPassword, teacher.getPassword())){
            return new ResponseEntity<>("Contraseña erronea",HttpStatus.FORBIDDEN);
        }

        teacher.setPassword(passwordEncoder.encode(password));
        teacherService.saveTeacher(teacher);

        return new ResponseEntity<>("Contraseña cambiada",HttpStatus.OK);
    }

    @PostMapping("/teacher")
    public ResponseEntity<?> registerTeacher( @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password){

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
            return new ResponseEntity<>("Ya exite un usuario con correo electronico",HttpStatus.FORBIDDEN);
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

        return new ResponseEntity<>("Profesor Registrado",HttpStatus.CREATED);

    }

    @GetMapping("/teacher/confirm/{token}")
    public ResponseEntity<?> confirmTeacher (@PathVariable String token, HttpServletResponse response) throws IOException {

        if (token.isEmpty()){
            return new ResponseEntity<>("No puede enviar un token vacio", HttpStatus.FORBIDDEN);
        }

        Teacher teacher = teacherService.findByToken(token);

        if (teacher != null) {


            if (teacher.getActive()) {
                return new ResponseEntity<>("El profesor ya se encontraba activado", HttpStatus.FORBIDDEN);
            }

            teacher.setActive(true);

            teacherService.saveTeacher(teacher);

            response.sendRedirect("/web/login-register.html?confirmed=true");

            return new ResponseEntity<>("Correo electronico de Profesor confirmado", HttpStatus.OK);

        }
        return new ResponseEntity<>("No se encontro ningun profesor con ese token", HttpStatus.FORBIDDEN);

    }

    @PatchMapping("/teacher/courses/delete")
    public ResponseEntity<?> deleteCourseTeacher(@RequestParam Long teacherID, @RequestParam Long courseID){

        Teacher teacher = teacherService.findTeacherById(teacherID);

        Course course = courseService.findCourseById(courseID);

        if (!teacher.getCourses().contains(course)){
            return new ResponseEntity<>("Este curso no esta asociado a este Profesor",HttpStatus.FORBIDDEN);
        }
        if (course == null){
            return new ResponseEntity<>("El curso no existe",HttpStatus.FORBIDDEN);
        }
        if (course.getTeacher() == null){
            return new ResponseEntity<>("Este Curso no tiene profesor",HttpStatus.FORBIDDEN);
        }

        course.setTeacher(null);

        courseService.saveCourse(course);

        return new ResponseEntity<>("Profesor borrado del curso exitosamente",HttpStatus.OK);
    }

    @PostMapping("/teacher/current/uploadImage")
    public ResponseEntity<?> saveImage(@RequestParam String image,Authentication authentication) {

        Teacher teacher = teacherService.findTeacherByEmail(authentication.getName());

        if (teacher == null){
            return new ResponseEntity<>("Debes iniciar sesion para realizar esta operacion",HttpStatus.FORBIDDEN);
        }
        if (image.isEmpty()){
            return new ResponseEntity<>("Seleccione una imagen",HttpStatus.FORBIDDEN);
        }

        teacher.setProfileImage(image);
        teacherService.saveTeacher(teacher);

        return new ResponseEntity<>("Imagen de perfil Cambiada", HttpStatus.OK);
    }

}
