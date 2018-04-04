package com.account.create;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.account.create.*")
public class CreateAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreateAccountApplication.class, args);
	}
}
