package com.example.librarymanagesystem.controller;

import com.example.librarymanagesystem.dto.ApiResponse;
import com.example.librarymanagesystem.dto.BookDTO;
import com.example.librarymanagesystem.model.Book;
import com.example.librarymanagesystem.service.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Book>> getBook(@PathVariable Integer bookId) {
        Book book = bookService.getBookById(bookId);

        if (book != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Get a book by bookId", book));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Book>>> getAllBook() {
        List<Book> bookList = bookService.getAllBook();

        if (bookList != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Get all book", bookList));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<Book>> createBook(@RequestBody BookDTO bookDTO) {
        int bookId = bookService.createBook(bookDTO);

        Book book = bookService.getBookById(bookId);


        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200, "Create a book successfully", book));
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Book>> updateBook(@PathVariable Integer bookId, @RequestBody BookDTO bookDTO) {
        Book book = bookService.getBookById(bookId);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        bookService.updateBook(bookId, bookDTO);

        Book updatedBook = bookService.getBookById(bookId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Update a book successfully", updatedBook));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResponse<?>> deleteBook(@PathVariable Integer bookId) {
        bookService.deleteBook(bookId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse<>(200, "Delete a book successfully", null));
    }
}
