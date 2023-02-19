package com.challenge.quinto.impacto;

import com.challenge.quinto.impacto.entities.*;
import com.challenge.quinto.impacto.repositories.CourseRepository;
import com.challenge.quinto.impacto.repositories.CourseStudentRepository;
import com.challenge.quinto.impacto.repositories.StudentRepository;
import com.challenge.quinto.impacto.repositories.TeacherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class QuintoImpactoApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuintoImpactoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(CourseRepository courseRepository, TeacherRepository teacherRepository, StudentRepository studentRepository, CourseStudentRepository courseStudentRepository) {
		return args -> {



			Teacher teacher1 = new Teacher("Alan", "Morua", "alanmorua8@gmail.com", "123", "a", "123");
			teacher1.setActive(true);
			teacher1.setAdmin(true);
			teacherRepository.save(teacher1);

			Course course1 = new Course("Java", "java course", "a", Shifts.MAÑANA);

			teacher1.addCourse(course1);

			courseRepository.save(course1);

			Student student1 = new Student("Juan", "Ramirez", "juan@mail.com","123", "a", "123");
			studentRepository.save(student1);

			CourseStudent courseStudent = new CourseStudent(course1, student1);
			courseStudentRepository.save(courseStudent);

			System.out.println("Started");

		};
	}
}
