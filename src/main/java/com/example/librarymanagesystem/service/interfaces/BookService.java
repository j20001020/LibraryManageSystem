package com.example.librarymanagesystem.service.interfaces;

import com.example.librarymanagesystem.dto.BookDTO;
import com.example.librarymanagesystem.model.Book;

import java.util.List;

public interface BookService {

    Book getBookById(Integer bookId);

    Integer createBook(BookDTO bookDTO);

    void updateBook(Integer bookId, BookDTO bookDTO);

    void deleteBook(Integer bookId);

    List<Book> getAllBook();
}
