package com.example.iread.books;

import com.example.iread.account.User;
import com.fasterxml.jackson.annotation.JsonProperty;

class Comment {
    public User user;
    @JsonProperty("book")
    public int bookId;
    public String comment;
    public String time;
}
