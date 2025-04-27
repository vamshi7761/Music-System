package com.musicsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MusicsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicsystemApplication.class, args);
	}

}
