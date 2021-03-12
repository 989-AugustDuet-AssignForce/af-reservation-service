package com.revature.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
	@Value("${revature.frontendBaseUrl}")
	private String frontendBaseUrl;
	
	@Value("${revature.locationServiceBaseUrl}")
	private String locationServiceBaseUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping( "/**" )
        		.allowedOrigins( frontendBaseUrl, locationServiceBaseUrl )
                .allowedMethods( "GET","PUT", "POST", "PATCH", "DELETE", "OPTIONS" )
                .allowedHeaders( "header1", "Content-Type", "Authorization" )
                .allowCredentials( true );
    }

}
