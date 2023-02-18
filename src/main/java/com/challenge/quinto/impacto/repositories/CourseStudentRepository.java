package com.challenge.quinto.impacto.repositories;

import com.challenge.quinto.impacto.entities.CourseStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CourseStudentRepository extends JpaRepository<CourseStudent, Long> {
}
