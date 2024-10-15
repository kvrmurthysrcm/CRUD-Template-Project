package com.productapi.config;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    Logger logger = LoggerFactory.getLogger("WebConfig.class");

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        logger.debug("Inside addCorsMappings()....");
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("POST","GET","PUT", "DELETE")
                // .allowedHeaders("header1", "header2", "header3")
                // .exposedHeaders("header1", "header2")
                .allowCredentials(true)
                .maxAge(3600);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}