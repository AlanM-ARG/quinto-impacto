package com.challenge.quinto.impacto.dtos;

import com.challenge.quinto.impacto.entities.Teacher;

import java.util.Set;
import java.util.stream.Collectors;

public class TeacherDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String profileImage;

    private Boolean active;

    private String token;

    private Boolean admin;

    Set<CourseDTO> coursesDTO;

    public TeacherDTO(Teacher teacher) {
        this.id = teacher.getId();
        this.firstName = teacher.getFirstName();
        this.lastName = teacher.getLastName();
        this.email = teacher.getEmail();
        this.password = teacher.getPassword();
        this.profileImage = teacher.getProfileImage();
        this.active = teacher.getActive();
        this.token = teacher.getToken();
        this.admin = teacher.getAdmin();
        this.coursesDTO = teacher.getCourses().stream().map(CourseDTO::new).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public Boolean getActive() {
        return active;
    }

    public String getToken() {
        return token;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public Set<CourseDTO> getCoursesDTO() {
        return coursesDTO;
    }
}
