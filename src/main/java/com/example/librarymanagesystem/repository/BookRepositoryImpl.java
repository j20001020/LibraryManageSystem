package com.example.librarymanagesystem.repository;

import com.example.librarymanagesystem.dto.BookDTO;
import com.example.librarymanagesystem.model.Book;
import com.example.librarymanagesystem.rowmapper.BookRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepositoryImpl {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    @Override
    public Book getBookById(Integer bookId) {
        String sql = "SELECT book_id, title, author, image_url, price, published_date, created_date, last_modified_date FROM book WHERE  book_id = :bookId";

        Map<String, Object> map = new HashMap<>();
        map.put("bookId", bookId);

        List<Book> bookList = jdbcTemplate.query(sql, map, new BookRowMapper());

        if (bookList.size() > 0) {
            return bookList.get(0);
        } else {
            return null;
        }
    }

//    @Override
    public int createBook(BookDTO bookDTO) {
        String sql = "INSERT INTO book(title, author, image_url, price, published_date, created_date, last_modified_date) " +
                "VALUES (:title, :author, :imageUrl, :price, :publishedDate, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("title", bookDTO.getTitle());
        map.put("author", bookDTO.getAuthor());
        map.put("imageUrl", bookDTO.getImageUrl());
        map.put("price", bookDTO.getPrice());
        map.put("publishedDate", bookDTO.getPublishedDate());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int bookId = keyHolder.getKey().intValue();

        return bookId;
    }

//    @Override
    public void updateBook(Integer bookId, BookDTO bookDTO) {
        String sql = "UPDATE book SET title = :title, author = :author, image_url = :imageUrl, price = :price, published_date = :publishedDate, " +
                "last_modified_date = :lastModifiedDate WHERE book_id = :bookId";

        Map<String, Object> map = new HashMap<>();
        map.put("bookId", bookId);

        map.put("title", bookDTO.getTitle());
        map.put("author", bookDTO.getAuthor());
        map.put("imageUrl", bookDTO.getImageUrl());
        map.put("price", bookDTO.getPrice());
        map.put("publishedDate", bookDTO.getPublishedDate());

        map.put("lastModifiedDate", new Date());

        jdbcTemplate.update(sql, map);
    }

//    @Override
    public void deleteBook(Integer bookId) {
        String sql = "DELETE FROM book WHERE book_id = :bookId";

        Map<String, Object> map = new HashMap<>();
        map.put("bookId", bookId);

        jdbcTemplate.update(sql, map);
    }

//    @Override
    public List<Book> getAllBook() {
        String sql = "SELECT book_id, title, author, image_url, price, published_date, created_date, last_modified_date FROM book";

        List<Book> bookList = jdbcTemplate.query(sql, new BookRowMapper());

        if (bookList.size() > 0) {
            return bookList;
        } else {
            return null;
        }
    }
}
