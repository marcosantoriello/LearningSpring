package org.example.ch3_1.main;

import org.example.ch3_1.config.ProjectConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new
				AnnotationConfigApplicationContext(ProjectConfig.class);

		Person person = context.getBean(Person.class);
		System.out.println("Person's name: " +  person.getName());

		Parrot par = context.getBean(Parrot.class);
		System.out.println("Parrot's name: " +  par.getName());

		System.out.println("Person's parrot: " + person.getParrot());
	}

}
