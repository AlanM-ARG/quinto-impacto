package com.challenge.quinto.impacto.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String title;

    private String category;

    private String duration;

    private String registered;

    private String description;

    private String coverPage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    Set<CourseStudent> courseStudents = new HashSet<>();

    public Course() {
    }

    public Course(String title, String category, String duration, String registered, String description, String coverPage) {
        this.title = title;
        this.category = category;
        this.duration = duration;
        this.registered = registered;
        this.description = description;
        this.coverPage = coverPage;
        this.teacher = null;
    }

    public Course(String title, String category, String duration, String registered, String description, String coverPage, Teacher teacher) {
        this.title = title;
        this.category = category;
        this.duration = duration;
        this.registered = registered;
        this.description = description;
        this.coverPage = coverPage;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverPage() {
        return coverPage;
    }

    public void setCoverPage(String coverPage) {
        this.coverPage = coverPage;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<CourseStudent> getCourseStudents() {
        return courseStudents;
    }

    public void setCourseStudents(Set<CourseStudent> courseStudents) {
        this.courseStudents = courseStudents;
    }
}
