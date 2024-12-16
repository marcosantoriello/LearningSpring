package org.example.ch2_2.config;

import org.example.ch2_2.main.Parrot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {
    @Bean
    Parrot parrot() {
        var p = new Parrot();
        p.setName("Koko");
        return p;
    }

    @Bean
    String helloWorld() {
        return "Hello, World!";
    }

    @Bean
    String secondStringBean() {
        return "Second String Bean";
    }


    @Bean
    Integer tenInt() {
        return 10;
    }
}
