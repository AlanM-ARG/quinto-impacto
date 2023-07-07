//package com.challenge.quinto.impacto;
//
// import com.challenge.quinto.impacto.entities.Course;
// import com.challenge.quinto.impacto.entities.CourseStudent;
// import com.challenge.quinto.impacto.entities.Student;
// import com.challenge.quinto.impacto.entities.Teacher;
// import com.challenge.quinto.impacto.repositories.CourseRepository;
// import com.challenge.quinto.impacto.repositories.CourseStudentRepository;
// import com.challenge.quinto.impacto.repositories.StudentRepository;
// import com.challenge.quinto.impacto.repositories.TeacherRepository;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.security.crypto.password.PasswordEncoder;
//
// import java.util.List;
//
// import static org.hamcrest.MatcherAssert.assertThat;
// import static org.hamcrest.Matchers.*;
// import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
//
// @DataJpaTest
// @AutoConfigureTestDatabase(replace = NONE)
// public class RepositoriesTest {
//
//
//     @Autowired
//     CourseStudentRepository courseStudentRepository;
//
//     @Autowired
//     CourseRepository courseRepository;
//
//     @Autowired
//     StudentRepository studentRepository;
//
//     @Autowired
//     TeacherRepository teacherRepository;
//
//     @MockBean
//     PasswordEncoder passwordEncoder;
//
//     @Test
//     public void existCourses() {
//
//         List<Course> courses = courseRepository.findAll();
//
//         assertThat(courses, is(not(empty())));
//
//     }
//
//     @Test
//     public void existProgrammingCourse() {
//
//         List<Course> courses = courseRepository.findAll();
//
//         assertThat(courses, hasItem(hasProperty("category", is("Programacion"))));
//
//     }
//
//     @Test
//     public void existStudents() {
//
//         List<Student> students = studentRepository.findAll();
//
//         assertThat(students, is(not(empty())));
//
//     }
//
//     @Test
//     public void existAdminStudent() {
//
//         List<Student> students = studentRepository.findAll();
//
//         assertThat(students, hasItem(hasProperty("email", is("admin@admin.com"))));
//
//     }
//
//     @Test
//     public void existTeachers() {
//
//         List<Teacher> teachers = teacherRepository.findAll();
//
//         assertThat(teachers, is(not(empty())));
//
//     }
//
//     @Test
//     public void existAnyTeacher() {
//
//         List<Teacher> teachers = teacherRepository.findAll();
//
//         assertThat(teachers, hasItem(hasProperty("lastName", is("Morua"))));
//
//     }
//
//     @Test
//     public void existCourseStudent() {
//
//         List<CourseStudent> courseStudents = courseStudentRepository.findAll();
//
//         assertThat(courseStudents, is(not(empty())));
//
//     }
//
//     @Test
//     public void existCourseStudentJava() {
//
//         List<CourseStudent> courseStudents = courseStudentRepository.findAll();
//
//         assertThat(courseStudents, hasItem(hasProperty("title", is("Curso Basico de Java"))));
//
//     }
//
// }
