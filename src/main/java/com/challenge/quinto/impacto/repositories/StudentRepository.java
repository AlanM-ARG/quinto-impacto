package com.challenge.quinto.impacto.repositories;

import com.challenge.quinto.impacto.entities.Course;
import com.challenge.quinto.impacto.entities.Student;
import com.challenge.quinto.impacto.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface StudentRepository extends JpaRepository<Student, Long> {


    @Query("SELECT s from Student s WHERE s.id = :id")
    Optional<Student> findById(@Param("id") Long id);

    @Query("SELECT s from Student s WHERE s.email = :email")
    Optional<Student> findByEmail(@Param("email") String email);

    @Query("SELECT s from Student s WHERE s.token = :token")
    Optional<Student> findByToken(@Param("token") String token);

    @Query("SELECT s from Student s")
    List<Student> findAll();

    @Query("SELECT s FROM Student s " +
            "LEFT JOIN CourseStudent cs ON s.id = cs.student.id " +
            "WHERE (CONCAT(s.firstName, ' ', s.lastName) LIKE %:name% " +
            "OR s.firstName LIKE %:name% " +
            "OR s.lastName LIKE %:name%) " +
            "AND (:idCourse IS NULL OR cs.course.id = :idCourse)")
    List<Student> findByNameAndCourse(@Param("name") String name, @Param("idCourse") Long idCourse);

}
