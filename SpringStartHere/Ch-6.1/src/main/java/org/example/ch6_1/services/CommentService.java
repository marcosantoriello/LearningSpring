package org.example.ch6_1.services;

import org.example.ch6_1.models.Comment;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CommentService {

    private Logger logger = Logger.getLogger(CommentService.class.getName());

    // scenario's use case
    public void publishComment(Comment comment) {
        logger.info("Publishing comment: " + comment.getContent());
    }

}
