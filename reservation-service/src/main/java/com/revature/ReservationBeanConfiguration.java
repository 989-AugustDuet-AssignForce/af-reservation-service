package com.revature;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
// @ComponentScan(basePackages = {"com.revature", "com.revature.controller", "com.revature.dto", "com.revature.model", "com.revature.repository"})
public class ReservationBeanConfiguration {
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}