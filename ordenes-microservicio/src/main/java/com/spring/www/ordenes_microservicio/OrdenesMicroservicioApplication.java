package com.spring.www.ordenes_microservicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class OrdenesMicroservicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdenesMicroservicioApplication.class, args);
	}

}
