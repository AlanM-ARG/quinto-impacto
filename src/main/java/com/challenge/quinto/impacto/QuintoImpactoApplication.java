package com.challenge.quinto.impacto;

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
	public CommandLineRunner initData() {
		return args -> {

			System.out.println("Started");

		};
	}
}
