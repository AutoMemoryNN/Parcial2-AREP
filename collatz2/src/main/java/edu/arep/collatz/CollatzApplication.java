package edu.arep.collatz;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CollatzApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(CollatzApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8084"));
		app.run(args);
	}
}
