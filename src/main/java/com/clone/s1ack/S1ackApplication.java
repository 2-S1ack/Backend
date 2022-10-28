package com.clone.s1ack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class S1ackApplication {

	public static void main(String[] args) {
		SpringApplication.run(S1ackApplication.class, args);
	}

}
