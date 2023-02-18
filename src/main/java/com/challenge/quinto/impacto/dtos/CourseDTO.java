package com.challenge.quinto.impacto.dtos;

import com.challenge.quinto.impacto.entities.Course;
import com.challenge.quinto.impacto.entities.CourseStudent;
import com.challenge.quinto.impacto.entities.Teacher;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseDTO {

    private Long id;

    private String title;

    private String category;

    private String duration;

    private String registered;

    private String description;

    private String coverPage;

    private String teacherFullName;

    private Set<StudentDTO> studentDTOS;
    public CourseDTO(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.category = course.getCategory();
        this.duration = course.getDuration();
        this.registered = course.getRegistered();
        this.description = course.getDescription();
        this.coverPage = course.getCoverPage();
        this.teacherFullName = course.getTeacher().getFirstName() + " " + course.getTeacher().getLastName();
        this.studentDTOS = course.getCourseStudents().stream().map(courseStudent -> new StudentDTO(courseStudent.getStudent())).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDuration() {
        return duration;
    }

    public String getRegistered() {
        return registered;
    }

    public String getDescription() {
        return description;
    }

    public String getCoverPage() {
        return coverPage;
    }

    public String getTeacherFullName() {
        return teacherFullName;
    }

    public Set<StudentDTO> getStudentDTOS() {
        return studentDTOS;
    }
}
