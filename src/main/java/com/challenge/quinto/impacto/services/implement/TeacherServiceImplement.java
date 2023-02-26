package com.challenge.quinto.impacto.services.implement;

import com.challenge.quinto.impacto.dtos.TeacherDTO;
import com.challenge.quinto.impacto.entities.Course;
import com.challenge.quinto.impacto.entities.Student;
import com.challenge.quinto.impacto.entities.Teacher;
import com.challenge.quinto.impacto.repositories.CourseRepository;
import com.challenge.quinto.impacto.repositories.StudentRepository;
import com.challenge.quinto.impacto.repositories.TeacherRepository;
import com.challenge.quinto.impacto.services.EmailService;
import com.challenge.quinto.impacto.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImplement implements TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public void saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Override
    public Set<TeacherDTO> getAllTeachersDTO() {
        return teacherRepository.findAll().stream().map(TeacherDTO::new).collect(Collectors.toSet());
    }

    @Override
    public Set<Teacher> getAllTeachers() {
        return new HashSet<>(teacherRepository.findAll());
    }

    @Override
    public TeacherDTO findTeacherDTOById(Long id) {
        return teacherRepository.findById(id).map(TeacherDTO::new).orElse(null);
    }

    @Override
    public Teacher findTeacherById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @Override
    public TeacherDTO findTeacherDTOByEmail(String email) {
        return teacherRepository.findByEmail(email).map(TeacherDTO::new).orElse(null);
    }

    @Override
    public Teacher findTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Set<String> getAllTokens() {
        return teacherRepository.findAll().stream().map(Teacher::getToken).collect(Collectors.toSet());
    }

    @Override
    public Teacher findByToken(String token) {
        return teacherRepository.findByToken(token).orElse(null);
    }

    @Override
    public ResponseEntity<?> changePasswordBody(String email, String password, String oldPassword) {

        Teacher teacher = teacherRepository.findByEmail(email).orElse(null);

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
        teacherRepository.save(teacher);

        return new ResponseEntity<>("Contraseña cambiada",HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> registerTeacherBody(String email, String firstName, String lastName, String password) {

        Student student = studentRepository.findByEmail(email).orElse(null);

        Teacher teacher = teacherRepository.findByEmail(email).orElse(null);

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

        Set<String> allTokens = teacherRepository.findAll().stream().map(Teacher::getToken).collect(Collectors.toSet());

        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (allTokens.contains(token));

        Teacher newTeacher = new Teacher(firstName, lastName, email, passwordEncoder.encode(password),"https://i.ibb.co/cFr5syK/educacion-en-el-hogar.png", token);

        teacherRepository.save(newTeacher);

        String link = "http://localhost:8080/api/teacher/confirm/" + token;

        emailService.sendEmail(newTeacher.getEmail(), emailService.buildEmail(newTeacher.getFirstName(), link));

        return new ResponseEntity<>("Profesor Registrado",HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<?> confirmTeacherBody(String token, HttpServletResponse response) throws IOException {

        if (token.isEmpty()){
            return new ResponseEntity<>("No puede enviar un token vacio", HttpStatus.FORBIDDEN);
        }

        Teacher teacher = teacherRepository.findByToken(token).orElse(null);

        if (teacher != null) {


            if (teacher.getActive()) {
                return new ResponseEntity<>("El profesor ya se encontraba activado", HttpStatus.FORBIDDEN);
            }

            teacher.setActive(true);

            teacherRepository.save(teacher);

            response.sendRedirect("/web/login-register.html?confirmed=true");

            return new ResponseEntity<>("Correo electronico de Profesor confirmado", HttpStatus.OK);

        }
        return new ResponseEntity<>("No se encontro ningun profesor con ese token", HttpStatus.FORBIDDEN);

    }

    @Override
    public ResponseEntity<?> deleteCourseTeacherBody(Long teacherID, Long courseID) {

        Teacher teacher = teacherRepository.findById(teacherID).orElse(null);

        Course course = courseRepository.findById(courseID).orElse(null);

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

        courseRepository.save(course);

        return new ResponseEntity<>("Profesor borrado del curso exitosamente",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> saveImageBody(String image, String email) {

        Teacher teacher = teacherRepository.findByEmail(email).orElse(null);

        if (teacher == null){
            return new ResponseEntity<>("Debes iniciar sesion para realizar esta operacion",HttpStatus.FORBIDDEN);
        }
        if (image.isEmpty()){
            return new ResponseEntity<>("Seleccione una imagen",HttpStatus.FORBIDDEN);
        }

        teacher.setProfileImage(image);
        teacherRepository.save(teacher);

        return new ResponseEntity<>("Imagen de perfil Cambiada", HttpStatus.OK);
    }

}
