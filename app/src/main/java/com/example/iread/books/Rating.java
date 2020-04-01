package com.example.iread.books;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rating {
    @JsonProperty("book")
    public int bookId;
    public float rating;
}
