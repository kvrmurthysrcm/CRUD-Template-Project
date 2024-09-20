package com.productapi;

import com.productapi.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@EnableConfigurationProperties(RsaKeyProperties.class)
@EnableCaching
@ComponentScan
// @EnableEurekaClient
// @EnableDiscoveryClient
@SpringBootApplication
public class CrudTemplateProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudTemplateProjectApplication.class, args);
	}

}
