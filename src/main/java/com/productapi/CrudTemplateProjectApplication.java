package com.productapi;

import com.productapi.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@EnableConfigurationProperties(RsaKeyProperties.class)
@EnableCaching
@ComponentScan
@EnableDiscoveryClient
@SpringBootApplication
// @ImportResource("classpath:applicationContext.xml")
public class CrudTemplateProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudTemplateProjectApplication.class, args);
	}
}