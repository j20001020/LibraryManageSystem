package com.example.librarymanagesystem.service;

import com.example.librarymanagesystem.dto.BookDTO;
import com.example.librarymanagesystem.model.Book;
import com.example.librarymanagesystem.repository.interfaces.BookRepository;
import com.example.librarymanagesystem.service.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book getBookById(Integer bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        return book.orElse(null); // if the book does not exist, then return null
    }

    @Override
    public Integer createBook(BookDTO bookDTO) {
        Book book = new Book();
        mapDtoToBook(book, bookDTO);

        bookRepository.save(book);

        return book.getBookId();
    }

    @Override
    public void updateBook(Integer bookId, BookDTO bookDTO) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            mapDtoToBook(book, bookDTO);
            bookRepository.save(book);
        }
    }

    @Override
    public void deleteBook(Integer bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    private void mapDtoToBook(Book book, BookDTO bookDTO) {
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setImageUrl(bookDTO.getImageUrl());
        book.setPrice(bookDTO.getPrice());
        book.setPublishedDate(bookDTO.getPublishedDate());
        /* CreatedDate and ModifiedDate are created automatically by JPA */
    }
}
