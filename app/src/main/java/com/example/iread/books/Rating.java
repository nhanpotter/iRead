package com.example.iread.books;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Rating {
    @JsonProperty("book")
    public int bookId;
    public float rating;
}
