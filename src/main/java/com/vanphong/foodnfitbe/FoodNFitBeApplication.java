package com.vanphong.foodnfitbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.vanphong.foodnfitbe")
@EnableScheduling
public class FoodNFitBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodNFitBeApplication.class, args);
	}

}
