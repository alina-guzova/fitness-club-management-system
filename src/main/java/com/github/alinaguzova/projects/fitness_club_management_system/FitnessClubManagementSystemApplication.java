package com.github.alinaguzova.projects.fitness_club_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FitnessClubManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnessClubManagementSystemApplication.class, args);
		// Подождать немного, чтобы все бины инициализировались
		try {
			Thread.sleep(2000); // 2 секунды
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		System.out.println("Used memory after init: " + usedMemory / (1024 * 1024) + " MB");
	}


}
