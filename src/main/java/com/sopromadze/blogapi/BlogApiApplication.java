package com.sopromadze.blogapi;

import com.sopromadze.blogapi.security.JwtAuthenticationFilter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.convert.Jsr310Converters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * Main application class for the Blog API.
 *
 * <p>This class serves as the entry point for the Spring Boot application and configures
 * essential beans and settings for the blog API system. It handles timezone configuration,
 * JWT authentication filter setup, and object mapping capabilities.</p>
 *
 * @version 1.0
 * @since 12 de junio de 2025
 */
@SpringBootApplication
@EntityScan(basePackageClasses = { BlogApiApplication.class, Jsr310Converters.class })
public class BlogApiApplication {

	/**
	 * Main method that starts the Spring Boot application
	 *
	 * @param args Command line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);
	}

	/**
	 * Post-construction initialization method that sets the default timezone to UTC
	 *
	 * <p>This ensures consistent date/time handling across different server environments
	 * and prevents timezone-related issues in date operations.</p>
	 */
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	/**
	 * Creates and configures the JWT authentication filter bean
	 *
	 * @return JwtAuthenticationFilter instance for handling JWT token authentication
	 */
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	/**
	 * Creates and configures the ModelMapper bean for object mapping
	 *
	 * <p>ModelMapper is used throughout the application to convert between
	 * different object types (DTOs, entities, etc.) reducing boilerplate code.</p>
	 *
	 * @return ModelMapper instance for object-to-object mapping operations
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}