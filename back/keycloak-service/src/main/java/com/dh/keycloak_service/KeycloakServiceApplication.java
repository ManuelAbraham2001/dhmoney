package com.dh.keycloak_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.dh.keycloak_service.Repository")
public class KeycloakServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeycloakServiceApplication.class, args);
	}

}
