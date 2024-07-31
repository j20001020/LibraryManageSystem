package com.example.librarymanagesystem.service;

import com.example.librarymanagesystem.dto.BookDTO;
import com.example.librarymanagesystem.model.Book;
import com.example.librarymanagesystem.repository.interfaces.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl underTest;

    @Test
    void getBookById_existBook_returnBook() {

        // Arrange
        Book book = new Book();
        book.setBookId(1);
        Integer bookId = 1;
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        // Act
        Book result = underTest.getBookById(bookId);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(bookId, result.getBookId());
    }

    @Test
    void getBookById_nonExistBook_returnNull() {

        // Arrange
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Book result = underTest.getBookById(1);

        // Assert
        Assertions.assertNull(result);
    }

    @Test
    void createBook_validBookDTO_returnBookId() {

        // Arrange
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Test Book");

        Book savedBook = new Book();
        savedBook.setBookId(1);

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        // Act
        Integer result = underTest.createBook(bookDTO);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void updateBook_existBook() {

        // Arrange
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Test Book");
        Integer bookId = 1;

        Book existingBook = new Book();
        existingBook.setBookId(1);

        when(bookRepository.findById(1)).thenReturn(Optional.of(existingBook));

        // Act
        underTest.updateBook(bookId, bookDTO);

        // Assert
        Assertions.assertEquals("Test Book", existingBook.getTitle());
        verify(bookRepository).save(existingBook);
    }

    @Test
    void updateBook_nonExistBook() {

        // Arrange
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Test Book");
        Integer bookId = 1;

        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        underTest.updateBook(bookId, bookDTO);

        // Assert
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void deleteBook() {

        // Arrange
        Integer bookId = 1;

        // Act
        underTest.deleteBook(bookId);

        // Assert
        verify(bookRepository).deleteById(bookId);
    }

    @Test
    void getAllBook_returnAllBook() {

        // Arrange
        List<Book> bookList = Arrays.asList(new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(bookList);

        // Act
        List<Book> result = underTest.getAllBook();

        // Assert
        Assertions.assertEquals(2, result.size());
        verify(bookRepository).findAll();
    }
}