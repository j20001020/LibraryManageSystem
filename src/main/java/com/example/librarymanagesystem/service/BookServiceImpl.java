package com.example.librarymanagesystem.service;

import com.example.librarymanagesystem.model.Book;
import com.example.librarymanagesystem.service.interfaces.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Override
    public Book getBookById(int bookId) {
        return null;
    }
}
