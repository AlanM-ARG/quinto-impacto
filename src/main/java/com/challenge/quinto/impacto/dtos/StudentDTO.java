package com.challenge.quinto.impacto.dtos;

import com.challenge.quinto.impacto.entities.Student;

import java.util.Set;
import java.util.stream.Collectors;

public class StudentDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String profileImage;

    private Boolean active;

    private String token;

    private Boolean admin;

    Set<String> coursesStudentsTitle;

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();
        this.password = student.getPassword();
        this.profileImage = student.getProfileImage();
        this.active = student.getActive();
        this.token = student.getToken();
        this.admin = student.getAdmin();
        this.coursesStudentsTitle = student.getCourseStudents().stream().map(courseStudent -> courseStudent.getCourse().getTitle()).collect(Collectors.toSet());
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

    public Set<String> getCoursesStudentsTitle() {
        return coursesStudentsTitle;
    }
}
