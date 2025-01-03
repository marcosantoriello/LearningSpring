package org.example.main;

import org.example.model.Comment;
import org.example.proxies.CommentNotificationProxy;
import org.example.proxies.EmailCommentNotificationProxy;
import org.example.repositories.CommentRepository;
import org.example.repositories.DBCommentRepository;
import org.example.services.CommentService;

public class Main {
    public static void main(String[] args) {

        var commentRepository = new DBCommentRepository();
        var commentNotificationProxy = new EmailCommentNotificationProxy();

        var commentService = new CommentService(commentRepository, commentNotificationProxy);

        var comment = new Comment("Laurentiu", "Demo Comment");

        commentService.publishComment(comment);

    }
}