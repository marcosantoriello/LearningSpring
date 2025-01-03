package org.example.ch6_1.main;

import org.example.ch6_1.config.ProjectConfig;
import org.example.ch6_1.models.Comment;
import org.example.ch6_1.services.CommentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        var service = context.getBean(CommentService.class);

        Comment comment = new Comment();
        comment.setAuthor("Marcus");
        comment.setContent("Demo comment");

        service.publishComment(comment);
    }

}
