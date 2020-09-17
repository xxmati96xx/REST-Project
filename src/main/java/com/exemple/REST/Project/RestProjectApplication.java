package com.exemple.REST.Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import javax.servlet.Filter;

@SpringBootApplication
@ComponentScan({"com.exemple.REST.Project"})
@EntityScan("com.exemple.REST.Project.Entity")
@EnableJpaRepositories("com.exemple.REST.Project.dao")
public class RestProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestProjectApplication.class, args);
	}
	@Bean
	public Filter filter(){
		ShallowEtagHeaderFilter filter=new ShallowEtagHeaderFilter();
		return filter;
	}
}
