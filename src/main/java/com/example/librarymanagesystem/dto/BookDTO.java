package com.example.librarymanagesystem.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BookDTO {

    private String title;

    private String author;

    private String imageUrl;

    private Integer price;

    private Date publishedDate;

}

