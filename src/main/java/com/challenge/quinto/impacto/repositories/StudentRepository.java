package com.challenge.quinto.impacto.repositories;

import com.challenge.quinto.impacto.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);
}
