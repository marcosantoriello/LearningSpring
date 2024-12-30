package org.example.model;

public class Comment {
    private String author;
    private String text;

    public Comment(String author, String text) {
        this.author = author;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}