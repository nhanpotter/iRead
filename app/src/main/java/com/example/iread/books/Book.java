package com.example.iread.books;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Book {
    public int id;
    public String uid;
    public String bookTitle;
    public String subject;
    public String summary;
    public String originalPublisher;
    public String digitalPublisher;
    public String itemFormat;
    public String language;
    public String itemCopyright;
    public String authorName;
    public String published;
    public String resourceUrl;
    public String cover;
    public String thumbnail;
    public float overallRating;

    public Genre genre;

    public String getBookTitle() {
        return bookTitle;
    }
}
