package com.challenge.quinto.impacto.controllers;

import com.challenge.quinto.impacto.dtos.StudentDTO;
import com.challenge.quinto.impacto.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    StudentService studentService;

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

        return studentService.registerStudentBody(email, firstName, lastName,password);

    }

    @GetMapping("/students/confirm/{token}")
    public ResponseEntity<?> confirmStudent (@PathVariable String token, HttpServletResponse response) throws IOException {

        return studentService.confirmStudentBody(token, response);

    }

    @PatchMapping("/students/current/changePassword")
    public ResponseEntity<?> changePassword(Authentication authentication, @RequestParam String password, @RequestParam String oldPassword){

        return  studentService.changePasswordBody(authentication.getName(), password, oldPassword);
    }

    @PatchMapping("/students/current/uploadImage")
    public ResponseEntity<?> saveImage(@RequestParam String image,Authentication authentication) {

        return studentService.saveImageBody(authentication.getName(), image);

    }
}
