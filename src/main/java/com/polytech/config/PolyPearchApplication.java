package com.polytech.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PolyPearchApplication {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "DEV");
		SpringApplication.run(PolyPearchApplication.class, args);
	}
}
