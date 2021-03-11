package com.revature.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        		.allowedOrigins("http://localhost:4200")
                .allowedMethods("GET","PUT", "POST", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("header1", "Content-Type", "Authorization")
                .allowCredentials(true);
    }

}