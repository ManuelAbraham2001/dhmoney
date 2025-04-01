package com.dh.activities_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ActivitiesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitiesServiceApplication.class, args);
	}

}
