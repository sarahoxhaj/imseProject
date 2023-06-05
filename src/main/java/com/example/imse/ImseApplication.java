package com.example.imse;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@RestController
public class ImseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImseApplication.class, args);
	}

}
