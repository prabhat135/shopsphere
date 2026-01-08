package com.shopsphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ShopsphereApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopsphereApplication.class, args);
	}

}
