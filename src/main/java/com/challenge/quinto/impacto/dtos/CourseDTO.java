package com.challenge.quinto.impacto.dtos;

import com.challenge.quinto.impacto.entities.Course;
import com.challenge.quinto.impacto.entities.Shifts;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseDTO {

    private Long id;

    private String title;

    private String description;

    private String coverPage;

    private Shifts shift;

    private Boolean enabled;

    private String category;

    private String teacherFullName;

    private Set<StudentDTO> studentDTOS;
    public CourseDTO(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.coverPage = course.getCoverPage();
        this.shift = course.getShift();
        this.category = course.getCategory();
        if(course.getTeacher() != null){
            this.teacherFullName = course.getTeacher().getFirstName() + " " + course.getTeacher().getLastName();
        }else{
            this.teacherFullName = "No tiene profesor";
        }
        this.studentDTOS = course.getCourseStudents().stream().map(courseStudent -> new StudentDTO(courseStudent.getStudent())).collect(Collectors.toSet());
        this.enabled = course.getEnabled();


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

    public String getCategory() {
        return category;
    }

    public String getTeacherFullName() {
        return teacherFullName;
    }

    public Set<StudentDTO> getStudentDTOS() {
        return studentDTOS;
    }

    public Boolean getEnabled() {
        return enabled;
    }
}
