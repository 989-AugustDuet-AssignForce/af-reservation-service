package com.revature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class ReservationServiceApplication {

	public static void main( String[] args ) {
		
		SpringApplication.run( ReservationServiceApplication.class, args );
	}
}