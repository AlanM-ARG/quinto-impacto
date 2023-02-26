package com.challenge.quinto.impacto.services.implement;

import com.challenge.quinto.impacto.dtos.CourseStudentDTO;
import com.challenge.quinto.impacto.entities.Course;
import com.challenge.quinto.impacto.entities.CourseStudent;
import com.challenge.quinto.impacto.entities.Student;
import com.challenge.quinto.impacto.repositories.CourseRepository;
import com.challenge.quinto.impacto.repositories.CourseStudentRepository;
import com.challenge.quinto.impacto.repositories.StudentRepository;
import com.challenge.quinto.impacto.services.CourseStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseStudentServiceImplement implements CourseStudentService {

    @Autowired
    CourseStudentRepository courseStudentRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Override
    public void saveCourseStudent(CourseStudent courseStudent) {
        courseStudentRepository.save(courseStudent);
    }

    @Override
    public Set<CourseStudentDTO> getAllCoursesStudentsDTO() {
        return courseStudentRepository.findAll().stream().map(CourseStudentDTO::new).collect(Collectors.toSet());
    }

    @Override
    public Set<CourseStudent> getAllCoursesStudents() {
        return new HashSet<>(courseStudentRepository.findAll());
    }

    @Override
    public CourseStudentDTO findCourseStudentDTOById(Long id) {
        return courseStudentRepository.findById(id).map(CourseStudentDTO::new).orElse(null);
    }

    @Override
    public CourseStudent findCourseStudentById(Long id) {
        return courseStudentRepository.findById(id).orElse(null);
    }

    @Override
    public CourseStudentDTO findCourseStudentDTOByTitle(String title) {
        return courseStudentRepository.findByTitle(title).map(CourseStudentDTO::new).orElse(null);
    }

    @Override
    public CourseStudent findCourseStudentByTitle(String title) {
        return courseStudentRepository.findByTitle(title).orElse(null);
    }

    @Override
    public void deleteCourseStudent(CourseStudent courseStudent) {
        courseStudentRepository.delete(courseStudent);
    }

    @Override
    public ResponseEntity<?> createCourseStudentBody(String email, Long courseID) {

        Student student = studentRepository.findByEmail(email).orElse(null);

        if(student != null){

            Set<Course> coursesStudent = student.getCourseStudents().stream().map(CourseStudent::getCourse).collect(Collectors.toSet());

            Course course = courseRepository.findById(courseID).orElse(null);

            if (course == null){
                return new ResponseEntity<>("El curso seleccionado no existe", HttpStatus.FORBIDDEN);
            }

            if (coursesStudent.contains(course)){
                return new ResponseEntity<>("El estudiante ya se encuentra inscripto a este curso", HttpStatus.FORBIDDEN);
            }

            CourseStudent courseStudent = new CourseStudent(course, student);

            courseStudentRepository.save(courseStudent);

            return new ResponseEntity<>("Inscripto correctamente",HttpStatus.OK);

        }

        return new ResponseEntity<>("Debes estas autenticado para inscribirte a un curso",HttpStatus.FORBIDDEN);
    }

}
