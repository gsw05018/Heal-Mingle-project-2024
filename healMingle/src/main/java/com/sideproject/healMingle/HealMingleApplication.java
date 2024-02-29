package com.sideproject.healMingle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class HealMingleApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealMingleApplication.class, args);
	}

}
