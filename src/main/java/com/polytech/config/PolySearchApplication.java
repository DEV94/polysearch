package com.polytech.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PolySearchApplication {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "PROD");
		SpringApplication.run(PolySearchApplication.class, args);
	}
}
