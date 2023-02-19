package com.challenge.quinto.impacto.dtos;

import com.challenge.quinto.impacto.entities.Course;
import com.challenge.quinto.impacto.entities.CourseStudent;
import com.challenge.quinto.impacto.entities.Shifts;
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

    private String description;

    private String coverPage;

    private Shifts shift;

    private String teacherFullName;

    private Set<StudentDTO> studentDTOS;
    public CourseDTO(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.coverPage = course.getCoverPage();
        this.shift = course.getShift();
        this.teacherFullName = course.getTeacher().getFirstName() + " " + course.getTeacher().getLastName();
        this.studentDTOS = course.getCourseStudents().stream().map(courseStudent -> new StudentDTO(courseStudent.getStudent())).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCoverPage() {
        return coverPage;
    }

    public Shifts getShift() {
        return shift;
    }

    public String getTeacherFullName() {
        return teacherFullName;
    }

    public Set<StudentDTO> getStudentDTOS() {
        return studentDTOS;
    }
}
