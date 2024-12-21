package org.example.ch3_1.config;

import org.example.ch3_1.main.Parrot;
import org.example.ch3_1.main.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {
    @Bean
    public Parrot parrot() {
        Parrot p =  new Parrot();
        p.setName("Koko");
        return p;
    }

    @Bean
    public Person person() {
        Person p =  new Person();
        p.setName("Augusto");
        p.setParrot(parrot());
        return p;
    }
}
