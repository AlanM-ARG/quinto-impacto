package com.challenge.quinto.impacto.controllers;

import com.challenge.quinto.impacto.dtos.TeacherDTO;
import com.challenge.quinto.impacto.services.CourseService;
import com.challenge.quinto.impacto.services.EmailService;
import com.challenge.quinto.impacto.services.StudentService;
import com.challenge.quinto.impacto.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

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

        return teacherService.changePasswordBody(authentication.getName(),password, oldPassword);
    }

    @PostMapping("/teacher")
    public ResponseEntity<?> registerTeacher( @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password){

        return teacherService.registerTeacherBody(email, firstName,lastName,password);

    }

    @GetMapping("/teacher/confirm/{token}")
    public ResponseEntity<?> confirmTeacher (@PathVariable String token, HttpServletResponse response) throws IOException {

        return teacherService.confirmTeacherBody(token, response);

    }

    @PatchMapping("/teacher/courses/delete")
    public ResponseEntity<?> deleteCourseTeacher(@RequestParam Long teacherID, @RequestParam Long courseID){

        return teacherService.deleteCourseTeacherBody(teacherID, courseID);

    }

    @PatchMapping("/teacher/current/uploadImage")
    public ResponseEntity<?> saveImage(@RequestParam String image,Authentication authentication) {

        return teacherService.saveImageBody(image, authentication.getName());

    }

}
