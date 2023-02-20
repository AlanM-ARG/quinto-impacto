package com.challenge.quinto.impacto.configurations;

import com.challenge.quinto.impacto.entities.Rol;
import com.challenge.quinto.impacto.entities.Student;
import com.challenge.quinto.impacto.entities.Teacher;
import com.challenge.quinto.impacto.services.StudentService;
import com.challenge.quinto.impacto.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(email -> {

            Student student = studentService.findStudentByEmail(email);
            Teacher teacher = teacherService.findTeacherByEmail(email);

            if (student != null) {
                if (student.getEmail().equals("admin@admin.com")) {
                    student.setAdmin(true);
                    studentService.saveStudent(student);
                    return new User(student.getEmail(), student.getPassword(),
                            AuthorityUtils.createAuthorityList(String.valueOf(Rol.ADMIN)));
                } else{
                    return new User(student.getEmail(), student.getPassword(),
                            AuthorityUtils.createAuthorityList(String.valueOf(Rol.STUDENT)));
                }
            } else if(teacher != null){
                if (teacher.getEmail().equals("admin@admin.com")) {
                    teacher.setAdmin(true);
                    teacherService.saveTeacher(teacher);
                    return new User(teacher.getEmail(), teacher.getPassword(),
                            AuthorityUtils.createAuthorityList(String.valueOf(Rol.ADMIN)));
                } else{
                    return new User(teacher.getEmail(), teacher.getPassword(),
                            AuthorityUtils.createAuthorityList(String.valueOf(Rol.TEACHER)));
                }
            } else {
                throw new UsernameNotFoundException("Unknown email: " + email);

            }

        });

    }


    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }

}
