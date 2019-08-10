package com.comercial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ComercialApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComercialApiApplication.class, args);
	}

}
