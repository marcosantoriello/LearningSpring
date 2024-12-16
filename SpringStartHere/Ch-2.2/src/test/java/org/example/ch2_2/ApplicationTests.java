package org.example.ch2_2;

import org.example.ch2_2.config.ProjectConfig;
import org.example.ch2_2.main.Parrot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = { ProjectConfig.class })
class ApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	@DisplayName("Test that a Parrot instance " +
			"with the attribute name having the value Koko " +
			"has been added to the Spring context.")
	public void testKokoIsInTheSpringContext() {
		Parrot p = applicationContext.getBean(Parrot.class);
		assertEquals("Koko", p.getName());
	}

	@Test
	@DisplayName("Test that a String instance " +
			"with the value Hello, World! " +
			"has been added to the Spring context")
	public void testHelloWorldIsInTheSpringContext() {
		// I have two beans of type String in the context, so I need to specify the name to reference it
		String toTest = applicationContext.getBean("helloWorld", String.class);
		assertEquals("Hello, World!", toTest);
	}

	@Test
	@DisplayName("Test that a Integer instance " +
			"with the value 10" +
			"has been added to the Spring context")
	public void testIntegerIsInTheSpringContext() {
		Integer toTest = applicationContext.getBean(Integer.class);
		assertEquals(10, toTest);
	}

}
