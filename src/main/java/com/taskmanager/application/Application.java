package com.taskmanager.application;

import java.sql.Date;
import java.util.stream.Stream;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.taskmanager.application.deletableentity.task.Task;
import com.taskmanager.application.deletableentity.task.TaskRepository;

/**
 * Main Application class that bootstraps and runs Spring. Usually extended to contain advanced Spring environment configurations.
 */

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}
