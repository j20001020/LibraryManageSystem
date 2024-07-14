package com.example.librarymanagesystem.model;

import lombok.Data;

import java.util.Date;

@Data
public class Book {

    private int bookId;

    private String title;

    private String author;

    private String imageUrl;

    private int price;

    private Date publishedDate;

    private Date createdDate;

    private Date lastModifiedDate;

}

