package com.spring.www.usuarios_microservicios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class UsuariosMicroserviciosApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuariosMicroserviciosApplication.class, args);
	}

}
