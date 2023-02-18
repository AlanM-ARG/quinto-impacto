package com.challenge.quinto.impacto.repositories;

import com.challenge.quinto.impacto.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
