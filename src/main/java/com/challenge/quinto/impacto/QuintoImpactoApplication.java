package com.challenge.quinto.impacto;

import com.challenge.quinto.impacto.repositories.CourseRepository;
import com.challenge.quinto.impacto.repositories.CourseStudentRepository;
import com.challenge.quinto.impacto.repositories.StudentRepository;
import com.challenge.quinto.impacto.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class QuintoImpactoApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuintoImpactoApplication.class, args);
	}


	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(CourseRepository courseRepository, TeacherRepository teacherRepository, StudentRepository studentRepository, CourseStudentRepository courseStudentRepository) {
		return args -> {



//			Teacher teacher = new Teacher("Alan", "Morua", "alanmorua8@gmail.com", passwordEncoder.encode("123"), "https://i.ibb.co/cFr5syK/educacion-en-el-hogar.png", "123");
//			Teacher teacher1 = new Teacher("Alberto", "Saucedo", "alberto@gmail.com", passwordEncoder.encode("123"), "https://i.ibb.co/cFr5syK/educacion-en-el-hogar.png", "123");
//			Teacher teacher2 = new Teacher("Juan", "Villalba", "juan1@gmail.com", passwordEncoder.encode("123"), "https://i.ibb.co/cFr5syK/educacion-en-el-hogar.png", "123");
//			Teacher teacher3 = new Teacher("Pedro", "Simone", "pedro@gmail.com", passwordEncoder.encode("123"), "https://i.ibb.co/cFr5syK/educacion-en-el-hogar.png", "123");
//			Teacher teacher4 = new Teacher("Lucas", "Castro", "lucas@gmail.com", passwordEncoder.encode("123"), "https://i.ibb.co/cFr5syK/educacion-en-el-hogar.png", "123");
//			Teacher teacher5 = new Teacher("David", "Bisbal", "david@gmail.com", passwordEncoder.encode("123"), "https://i.ibb.co/cFr5syK/educacion-en-el-hogar.png", "123");
//
//			teacher.setActive(true);
//			teacher1.setActive(true);
//			teacher2.setActive(true);
//			teacher3.setActive(true);
//			teacher4.setActive(true);
//			teacher5.setActive(true);
//
//			teacher.setAdmin(true);
//
//			teacherRepository.save(teacher);
//			teacherRepository.save(teacher1);
//			teacherRepository.save(teacher2);
//			teacherRepository.save(teacher3);
//			teacherRepository.save(teacher4);
//			teacherRepository.save(teacher5);
//
//			Course course = new Course("Curso Basico de Java", "Aprende las características fundamentales del lenguaje Java y desarrolla tus propios proyectos.", "https://cdn-icons-png.flaticon.com/512/1183/1183618.png", Shifts.valueOf("MAÑANA"), "Programacion");
//			Course course1 = new Course("Curso Basico de JavaScript", "Aprende las características fundamentales del lenguaje JavaScript y desarrolla tus propios proyectos.", "https://cdn-icons-png.flaticon.com/512/9695/9695720.png", Shifts.valueOf("TARDE"), "Programacion");
//			Course course2 = new Course("Curso Basico de HTML5", "Aprende las características fundamentales de HTML5 y maqueta tus propios proyectos.", "https://cdn-icons-png.flaticon.com/512/5486/5486380.png", Shifts.valueOf("NOCHE"), "Programacion");
//			Course course3 = new Course("Curso Basico de CSS3", "Aprende las características fundamentales de CSS3 y dale estilos a tus proyectos.", "https://cdn-icons-png.flaticon.com/512/5812/5812775.png", Shifts.valueOf("MAÑANA"), "Programacion");
//			Course course4 = new Course("Curso Basico de MySQL", "Aprende las características fundamentales de MySQL y desarrolla tus propias bases de datos.", "https://cdn-icons-png.flaticon.com/512/1199/1199128.png", Shifts.valueOf("TARDE"), "Programacion");
//			Course course5 = new Course("Curso Basico de PostgreSQL", "Aprende las características fundamentales de PostgreSQL y desarrolla tus propias bases de datos.", "https://upload.wikimedia.org/wikipedia/commons/2/29/Postgresql_elephant.svg", Shifts.valueOf("NOCHE"), "Programacion");
//			Course course6 = new Course("Curso Basico de Testing", "Aprende las características fundamentales de Testing.", "https://cdn-icons-png.flaticon.com/512/9160/9160747.png", Shifts.valueOf("NOCHE"), "Programacion");
//
//
//
//			teacher.addCourse(course);
//			teacher1.addCourse(course1);
//			teacher2.addCourse(course2);
//			teacher3.addCourse(course3);
//			teacher4.addCourse(course4);
//			teacher5.addCourse(course5);
//
//
//			courseRepository.save(course);
//			courseRepository.save(course1);
//			courseRepository.save(course2);
//			courseRepository.save(course3);
//			courseRepository.save(course4);
//			courseRepository.save(course5);
//			courseRepository.save(course6);
//
//
//			Student student = new Student("Juan", "Ramirez", "juan@mail.com",passwordEncoder.encode("123"), "https://i.ibb.co/yFKrNwB/estudio.png", "123");
//			Student student1 = new Student("Fabiana", "Casco", "fabi@mail.com",passwordEncoder.encode("123"), "https://i.ibb.co/yFKrNwB/estudio.png", "123");
//			Student student2 = new Student("Fernando", "Morua", "fer@mail.com",passwordEncoder.encode("123"), "https://i.ibb.co/yFKrNwB/estudio.png", "123");
//			Student student3 = new Student("Angel", "Casco", "angel@mail.com",passwordEncoder.encode("123"), "https://i.ibb.co/yFKrNwB/estudio.png", "123");
//			Student student4 = new Student("Alma", "Casco", "alma@mail.com",passwordEncoder.encode("123"), "https://i.ibb.co/yFKrNwB/estudio.png", "123");
//			Student student5 = new Student("Brenda", "Alegre", "bren@mail.com",passwordEncoder.encode("123"), "https://i.ibb.co/yFKrNwB/estudio.png", "123");
//			Student student6 = new Student("admin", "admin", "admin@admin.com",passwordEncoder.encode("123"), "https://i.ibb.co/yFKrNwB/estudio.png", "123");
//
//			student.setActive(true);
//			student1.setActive(true);
//			student2.setActive(true);
//			student3.setActive(true);
//			student4.setActive(true);
//			student5.setActive(true);
//			student6.setActive(true);
//
//			student6.setAdmin(true);
//
//			studentRepository.save(student);
//			studentRepository.save(student1);
//			studentRepository.save(student2);
//			studentRepository.save(student3);
//			studentRepository.save(student4);
//			studentRepository.save(student5);
//			studentRepository.save(student6);
//
//			CourseStudent courseStudent = new CourseStudent(course, student);
//			CourseStudent courseStudent1 = new CourseStudent(course1, student1);
//			CourseStudent courseStudent2 = new CourseStudent(course2, student2);
//			CourseStudent courseStudent3 = new CourseStudent(course3, student3);
//			CourseStudent courseStudent4 = new CourseStudent(course4, student4);
//			CourseStudent courseStudent5 = new CourseStudent(course5, student5);
//
//
//			courseStudentRepository.save(courseStudent);
//			courseStudentRepository.save(courseStudent1);
//			courseStudentRepository.save(courseStudent2);
//			courseStudentRepository.save(courseStudent3);
//			courseStudentRepository.save(courseStudent4);
//			courseStudentRepository.save(courseStudent5);


			System.out.println("Started");

		};
	}
}
