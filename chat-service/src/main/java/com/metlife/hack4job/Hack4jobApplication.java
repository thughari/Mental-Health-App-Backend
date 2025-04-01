package com.metlife.hack4job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Hack4jobApplication {

	public static void main(String[] args) {
		SpringApplication.run(Hack4jobApplication.class, args);
	}

}
