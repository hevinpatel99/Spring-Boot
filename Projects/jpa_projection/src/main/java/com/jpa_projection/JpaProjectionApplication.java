package com.jpa_projection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class JpaProjectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaProjectionApplication.class, args);
	}

}
