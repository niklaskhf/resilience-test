package com.example.PatternService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class PatternServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatternServiceApplication.class, args);
	}

}
