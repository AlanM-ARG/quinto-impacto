package com.challenge.quinto.impacto.services.implement;

import com.challenge.quinto.impacto.dtos.CourseDTO;
import com.challenge.quinto.impacto.entities.Course;
import com.challenge.quinto.impacto.entities.CourseStudent;
import com.challenge.quinto.impacto.entities.Shifts;
import com.challenge.quinto.impacto.entities.Teacher;
import com.challenge.quinto.impacto.repositories.CourseRepository;
import com.challenge.quinto.impacto.repositories.TeacherRepository;
import com.challenge.quinto.impacto.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImplement implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public Set<CourseDTO> getAllCoursesDTO() {
        return courseRepository.findAll().stream().map(CourseDTO::new).collect(Collectors.toSet());
    }

    @Override
    public Set<Course> getAllCourses() {
        return new HashSet<>(courseRepository.findAll());
    }

    @Override
    public CourseDTO findCourseDTOById(Long id) {
        return courseRepository.findById(id).map(CourseDTO::new).orElse(null);
    }

    @Override
    public Course findCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public CourseDTO findCourseDTOByTitle(String title) {
        return courseRepository.findByTitle(title).map(CourseDTO::new).orElse(null);
    }

    @Override
    public Course findCourseByTitle(String title) {
        return courseRepository.findByTitle(title).orElse(null);
    }

    @Override
    public void deleteStudentCourse(Course course, CourseStudent courseStudent) {
        course.getCourseStudents().remove(courseStudent);
        courseRepository.save(course);
    }

    @Override
    public Set<Course> getCoursesActives() {
        return courseRepository.findAll().stream().filter(Course::getEnabled).collect(Collectors.toSet());
    }

    @Override
    public ResponseEntity<?> createCourseBody(String title, String description, String shifts, String category, String coverPage) {

        Course course = courseRepository.findByTitle(title).orElse(null);

        if (title.isEmpty()){
            return new ResponseEntity<>("Ingrese un titulo", HttpStatus.FORBIDDEN);
        }
        if (description.isEmpty()){
            return new ResponseEntity<>("Ingrese una descripcion",HttpStatus.FORBIDDEN);
        }
        if (coverPage.isEmpty()){
            return new ResponseEntity<>("Ingrese una imagen",HttpStatus.FORBIDDEN);
        }
        if (category.isEmpty()){
            return new ResponseEntity<>("Ingrese una categoria",HttpStatus.FORBIDDEN);
        }
        if (course != null){
            return new ResponseEntity<>("Ya existe un curso con ese titulo",HttpStatus.FORBIDDEN);
        }
        if (!shifts.equals("MAÑANA") && !shifts.equals("TARDE") && !shifts.equals("NOCHE")){
            return new ResponseEntity<>("Ingrese un turno valido",HttpStatus.FORBIDDEN);
        }

        courseRepository.save(new Course(title, description, coverPage, Shifts.valueOf(shifts), category));

        return new ResponseEntity<>("Curso creado exitosamente",HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> changeCourseTeacherBody(Long courseID, Long teacherID) {

        Course course = courseRepository.findById(courseID).orElse(null);

        Teacher teacher = teacherRepository.findById(teacherID).orElse(null);

        if (course == null){
            return new ResponseEntity<>("El curso no existe",HttpStatus.FORBIDDEN);
        }

        if (teacher == null){
            return new ResponseEntity<>("El profesor no existe",HttpStatus.FORBIDDEN);
        }

        course.setTeacher(teacher);

        courseRepository.save(course);

        return new ResponseEntity<>("Profesor cambiado con exito",HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<?> deleteCourseTeacherBody(Long courseID) {

        Course course = courseRepository.findById(courseID).orElse(null);

        if (course == null){
            return new ResponseEntity<>("El curso no existe",HttpStatus.FORBIDDEN);
        }
        if (course.getTeacher() == null){
            return new ResponseEntity<>("Este curso no tiene profesor",HttpStatus.FORBIDDEN);
        }

        course.setTeacher(null);

        courseRepository.save(course);

        return new ResponseEntity<>("El profesor de este curso fue borrado",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> disableCourseBody(Long id) {

        Set<Course> coursesActives = courseRepository.findAll().stream().filter(Course::getEnabled).collect(Collectors.toSet());

        Course courseSelected = courseRepository.findById(id).orElse(null);

        if (id == null || id <= 0) {
            return new ResponseEntity<>("Ingrese un id valido",HttpStatus.FORBIDDEN);
        }
        if (courseSelected == null) {
            return new ResponseEntity<>("El curso seleccionado no existe",HttpStatus.FORBIDDEN);
        }
        if (!coursesActives.contains(courseSelected)) {
            return new ResponseEntity<>("El curso ya se encuentra desactivado",HttpStatus.FORBIDDEN);
        }

        courseSelected.setEnabled(false);

        courseRepository.save(courseSelected);

        return new ResponseEntity<>("Curso desactivado",HttpStatus.OK);
    }


}
