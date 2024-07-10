package com.example.librarymanagesystem.repository;

import com.example.librarymanagesystem.model.Book;
import com.example.librarymanagesystem.repository.interfaces.BookRepository;
import com.example.librarymanagesystem.rowmapper.BookRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book getBookById(int bookId) {
        String sql = "SELECT book_id, title, author, image_url, price, published_date, created_date, last_modified_date FROM book WHERE  book_id = :bookId";

        Map<String, Object> map = new HashMap<>();
        map.put("bookId", bookId);

        List<Book> bookList = jdbcTemplate.query(sql, map, new BookRowMapper());

        if(bookList.size() > 0){
            return bookList.get(0);
        }else {
            return null;
        }
    }
}
