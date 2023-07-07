package com.challenge.quinto.impacto.repositories;

import com.challenge.quinto.impacto.entities.Course;
import com.challenge.quinto.impacto.entities.CourseStudent;
import com.challenge.quinto.impacto.entities.Student;
import com.challenge.quinto.impacto.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface CourseStudentRepository extends JpaRepository<CourseStudent, Long> {

    @Query("SELECT cs from CourseStudent cs WHERE cs.id = :id")
    Optional<CourseStudent> findById(@Param("id") Long id);
    @Query("SELECT cs from CourseStudent cs WHERE cs.title LIKE %:title%")
    Optional<CourseStudent> findByTitle(@Param("title") String title);

    @Query("SELECT cs from CourseStudent cs")
    List<CourseStudent> findAll();

}
