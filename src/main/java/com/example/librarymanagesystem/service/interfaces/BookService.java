package com.example.librarymanagesystem.service.interfaces;

import com.example.librarymanagesystem.dto.BookDTO;
import com.example.librarymanagesystem.model.Book;

import java.util.List;

public interface BookService {

    Book getBookById(int bookId);

    int createBook(BookDTO bookDTO);

    void updateBook(int bookId, BookDTO bookDTO);

    void deleteBook(int bookId);

    List<Book> getAllBook();
}
