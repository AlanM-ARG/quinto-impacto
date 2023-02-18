package com.challenge.quinto.impacto.repositories;

import com.challenge.quinto.impacto.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByTitle(String title);


}
