package com.challenge.quinto.impacto.services.implement;

import com.challenge.quinto.impacto.dtos.StudentDTO;
import com.challenge.quinto.impacto.entities.Student;
import com.challenge.quinto.impacto.entities.Teacher;
import com.challenge.quinto.impacto.repositories.StudentRepository;
import com.challenge.quinto.impacto.repositories.TeacherRepository;
import com.challenge.quinto.impacto.services.EmailService;
import com.challenge.quinto.impacto.services.StudentService;
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
public class StudentServiceImplement implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Set<StudentDTO> getAllStudentDTO() {
        return studentRepository.findAll().stream().map(StudentDTO::new).collect(Collectors.toSet());
    }

    @Override
    public Set<Student> getAllStudent() {
        return new HashSet<>(studentRepository.findAll());
    }

    @Override
    public StudentDTO findStudentDTOById(Long id) {
        return studentRepository.findById(id).map(StudentDTO::new).orElse(null);
    }

    @Override
    public Student findStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public StudentDTO findStudentDTOByEmail(String email) {
        return studentRepository.findByEmail(email).map(StudentDTO::new).orElse(null);
    }

    @Override
    public Student findStudentByEmail(String email) {
        return studentRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Set<String> getAllTokens() {
        return studentRepository.findAll().stream().map(Student::getToken).collect(Collectors.toSet());
    }

    @Override
    public Student findByToken(String token) {
        return studentRepository.findByToken(token).orElse(null);
    }

    @Override
    public ResponseEntity<?> registerStudentBody(String email, String firstName, String lastName, String password) {

        Student student = studentRepository.findByEmail(email).orElse(null);

        Teacher teacher = teacherRepository.findByEmail(email).orElse(null);

        if (firstName.isEmpty() ){
            return new ResponseEntity<>("Ingrese un nombre", HttpStatus.FORBIDDEN);
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

        Set<String> allTokens = studentRepository.findAll().stream().map(Student::getToken).collect(Collectors.toSet());

        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (allTokens.contains(token));

        Student newStudent = new Student(firstName, lastName, email, passwordEncoder.encode(password),"https://i.ibb.co/yFKrNwB/estudio.png", token);

        studentRepository.save(newStudent);

        String link = "https://quinto-impacto-production.up.railway.app/api/students/confirm/" + token;

        emailService.sendEmail(newStudent.getEmail(), emailService.buildEmail(newStudent.getFirstName(), link));

        return new ResponseEntity<>("Estudiante registrado con exito",HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> confirmStudentBody(String token, HttpServletResponse response) throws IOException {
        if (token.isEmpty()){
            return new ResponseEntity<>("No puede enviar un token vacio", HttpStatus.FORBIDDEN);
        }

        Student student = studentRepository.findByToken(token).orElse(null);

        if (student != null) {


            if (student.getActive()) {
                return new ResponseEntity<>("El estudiante ya se encontraba activado", HttpStatus.FORBIDDEN);
            }

            student.setActive(true);

            studentRepository.save(student);

            response.sendRedirect("/web/login-register.html?confirmed=true");

            return new ResponseEntity<>("Correo electronico de estudiante confirmado", HttpStatus.OK);

        }
        return new ResponseEntity<>("No se encontro ningun estudiante con ese token", HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<?> changePasswordBody(String email, String password, String oldPassword) {

        Student student = studentRepository.findByEmail(email).orElse(null);

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
        studentRepository.save(student);

        return new ResponseEntity<>("Contraseña cambiada",HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> saveImageBody(String email, String image) {

        Student student = studentRepository.findByEmail(email).orElse(null);

        if (student == null){
            return new ResponseEntity<>("Debes iniciar sesion para realizar esta operacion",HttpStatus.FORBIDDEN);
        }
        if (image.isEmpty()){
            return new ResponseEntity<>("Seleccione una imagen",HttpStatus.FORBIDDEN);
        }

        student.setProfileImage(image);
        studentRepository.save(student);

        return new ResponseEntity<>("Imagen de perfil Cambiada", HttpStatus.OK);
    }
}
