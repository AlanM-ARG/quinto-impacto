package com.challenge.quinto.impacto.dtos;

import com.challenge.quinto.impacto.entities.CourseStudent;


public class CourseStudentDTO {

    private Long id;

    private String courseTitle;

    private String studentFullName;


    public CourseStudentDTO(CourseStudent courseStudent) {
        this.id = courseStudent.getId();
        this.courseTitle = courseStudent.getTitle();
        this.studentFullName = courseStudent.getStudent().getFirstName() + " " + courseStudent.getStudent().getLastName();
    }

    public Long getId() {
        return id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getStudentFullName() {
        return studentFullName;
    }
}
