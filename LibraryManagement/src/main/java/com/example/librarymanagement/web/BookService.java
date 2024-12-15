package com.example.librarymanagement.web;

import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(BookService.class);

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Long saveBook(Book book) {
        Long res =  this.bookRepository.save(book).getId();
        log.info("Book saved: {}", res);
        return res;
    }

    public List<Book> getAllBooks() { return bookRepository.findAll(); }

    public List<Book> getBookByTitle(String title) { return bookRepository.findByTitle(title); }

    public List<Book> getBookByAuthor(String author)  { return bookRepository.findByAuthor(author); }

    public List<Book> getBookByISBN(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
}
