package com.challenge.quinto.impacto.repositories;

import com.challenge.quinto.impacto.entities.Course;
import com.challenge.quinto.impacto.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c from Course c WHERE c.id = :id")
    Optional<Course> findById(@Param("id") Long id);

    @Query("SELECT c from Course c WHERE c.title LIKE %:title%")
    Optional<Course> findByTitle(@Param("title") String title);

    @Query("SELECT c from Course c")
    List<Course> findAll();

    @Query("SELECT c from Course c WHERE c.enabled IS true")
    List<Course> findAllActives();



}
