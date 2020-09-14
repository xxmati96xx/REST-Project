package com.exemple.REST.Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.exemple.REST.Project"})
@EntityScan("com.exemple.REST.Project.Entity")
@EnableJpaRepositories("com.exemple.REST.Project.dao")
public class RestProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestProjectApplication.class, args);
	}

}
