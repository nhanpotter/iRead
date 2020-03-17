package com.example.iread;

import java.util.Date;

class Book {
    private int id;
    private String uid;
    private String title;
    private String author;
    /*private List<Genre> genre;*/
    private String url;
    private String thumbnail;
    private String summary;
    private String language;
    private Date publishedDate;
    private String ISBN;

    Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
