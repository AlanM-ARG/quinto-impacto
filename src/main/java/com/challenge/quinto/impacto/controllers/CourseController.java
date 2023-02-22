package com.challenge.quinto.impacto.controllers;

import com.challenge.quinto.impacto.dtos.CourseDTO;
import com.challenge.quinto.impacto.entities.Course;
import com.challenge.quinto.impacto.entities.Shifts;
import com.challenge.quinto.impacto.entities.Teacher;
import com.challenge.quinto.impacto.services.CourseService;
import com.challenge.quinto.impacto.services.StudentService;
import com.challenge.quinto.impacto.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;


    @GetMapping("/courses/{id}")
    public CourseDTO getCourseDTO(@PathVariable Long id){
        return courseService.findCourseDTOById(id);
    }

    @GetMapping("/courses")
    public Set<CourseDTO> getAllCoursesDTO(){
        return courseService.getAllCoursesDTO();
    }

    @GetMapping("/courses/active")
    public Set<CourseDTO> getAllCoursesDTOActives(){
        return courseService.getCoursesActives().stream().map(CourseDTO::new).collect(Collectors.toSet());
    }

    @GetMapping("/courses/current")
    public Set<CourseDTO> getCoursesCurrentDTO(Authentication authentication){
        return studentService.findStudentByEmail(authentication.getName()).getCourseStudents().stream().map(courseStudent -> new CourseDTO(courseStudent.getCourse())).collect(Collectors.toSet());
    }

    @PostMapping("/courses")
    public ResponseEntity<?> createCourse(@RequestParam String title,@RequestParam String description,@RequestParam String coverPage, @RequestParam String shifts, @RequestParam String category){

        Course course = courseService.findCourseByTitle(title);

        if (title.isEmpty()){
            return new ResponseEntity<>("Ingrese un titulo",HttpStatus.FORBIDDEN);
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

        courseService.saveCourse(new Course(title, description, coverPage, Shifts.valueOf(shifts), category));

        return new ResponseEntity<>("Curso creado exitosamente",HttpStatus.CREATED);
    }

    @PatchMapping("/courses/teacher")
    public ResponseEntity<?> changeCourseTeacher (@RequestParam Long courseID, @RequestParam Long teacherID){

        Course course = courseService.findCourseById(courseID);

        Teacher teacher = teacherService.findTeacherById(teacherID);

        if (course == null){
            return new ResponseEntity<>("El curso no existe",HttpStatus.FORBIDDEN);
        }

        if (teacher == null){
            return new ResponseEntity<>("El profesor no existe",HttpStatus.FORBIDDEN);
        }

        course.setTeacher(teacher);

        courseService.saveCourse(course);

        return new ResponseEntity<>("Profesor cambiado con exito",HttpStatus.ACCEPTED);
    }

    @PatchMapping("/courses/teacher/delete")
    public ResponseEntity<?> deleteCourseTeacher(@RequestParam Long courseID){

        Course course = courseService.findCourseById(courseID);

        if (course == null){
            return new ResponseEntity<>("El curso no existe",HttpStatus.FORBIDDEN);
        }
        if (course.getTeacher() == null){
            return new ResponseEntity<>("Este curso no tiene profesor",HttpStatus.FORBIDDEN);
        }

        course.setTeacher(null);

        courseService.saveCourse(course);

        return new ResponseEntity<>("El profesor de este curso fue borrado",HttpStatus.OK);

    }

    @PatchMapping("/course/disable/{id}")
    public ResponseEntity<?> disableCourse(@PathVariable Long id) {

            Set<Course> coursesActives = courseService.getCoursesActives();

            Course courseSelected = courseService.findCourseById(id);

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
            courseService.saveCourse(courseSelected);

            return new ResponseEntity<>("Curso desactivado",HttpStatus.OK);

    }

}
