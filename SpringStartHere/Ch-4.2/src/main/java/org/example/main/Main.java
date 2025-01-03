package org.example.main;

import org.example.config.ProjectConfig;
import org.example.repositories.DBCommentRepository;
import org.example.services.CommentService;
import org.example.model.Comment;
import org.example.proxies.EmailCommentNotificationProxy;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        var comment = new Comment("Augusto", "Hello, World!");

        var commentService = context.getBean(CommentService.class);
        commentService.publishComment(comment);

    }
}