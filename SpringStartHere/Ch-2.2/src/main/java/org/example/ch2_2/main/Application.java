package org.example.ch2_2.main;

import org.example.ch2_2.config.ProjectConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

		Parrot p = context.getBean(Parrot.class); // getting the reference of a bean of type Parrot from the context
		System.out.println(p.getName());

		String helloWorld = context.getBean("helloWorld", String.class);
		System.out.println(helloWorld);

		String secondStringBean = context.getBean("secondStringBean", String.class);
		System.out.println(secondStringBean);

		Integer tenInt = context.getBean(Integer.class);
		System.out.println(tenInt);


	}

}
