package com.samwanjo.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.server.mvc.common.MvcUtils;

@SpringBootApplication
public class MsSpringSecureGatewayApplication {

	public static void main(String[] args) {

		SpringApplication.run(MsSpringSecureGatewayApplication.class, args);
	}

}
