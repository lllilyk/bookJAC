package com.example.bookjac.domain;

import lombok.Data;

@Data
public class Book {
    private int id;
    private String title;
    private String writer;
    private String publisher;
    private String categoryId;
    private String inPrice;
    private String outPrice;
    private String totalCount;
    private int inCount;
    private int displayCount;

}