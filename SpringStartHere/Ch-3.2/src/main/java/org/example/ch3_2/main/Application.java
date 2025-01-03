package org.example.ch3_2.main;

import org.example.ch3_2.beans.Person;
import org.example.ch3_2.config.ProjectConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(ProjectConfig.class);

		System.out.println("Person's parrot: " + context.getBean(Person.class).getParrot().getName());
	}

}
