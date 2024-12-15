package com.example.librarymanagement.web;

import com.example.librarymanagement.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @ModelAttribute
    public String bookList(
            @RequestParam(value = "title" , required = false) String title,
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "author", required = false) String author,
            Model model) {
        List<Book> books;

        if (title != null && !title.isEmpty()) {
            books = bookService.getBookByTitle(title);
        } else if (isbn != null && !isbn.isEmpty()) {
            books = bookService.getBookByISBN(isbn);
        } else if (author != null && !author.isEmpty()) {
            log.info("Author {}", author);
            books = bookService.getBookByAuthor(author);
        } else {
            books = bookService.getAllBooks();
        }

        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/add")
    public String addBookForm() {
        return "addBook";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book newBook) {
        log.info("Adding new book: {}", newBook);
        bookService.saveBook(newBook);
        return "redirect:/books";
    }
}
