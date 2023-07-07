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
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("SELECT t from Teacher t WHERE t.id = :id")
    Optional<Teacher> findById(@Param("id") Long id);

    @Query("SELECT t from Teacher t WHERE t.email = :email")
    Optional<Teacher> findByEmail(@Param("email") String email);

    @Query("SELECT t from Teacher t WHERE t.token = :token")
    Optional<Teacher> findByToken(@Param("token") String token);

    @Query("SELECT t from Teacher t")
    List<Teacher> findAll();

}
