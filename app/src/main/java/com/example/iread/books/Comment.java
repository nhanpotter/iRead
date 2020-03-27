package com.example.iread.books;

import com.example.iread.account.User;
import com.google.gson.annotations.SerializedName;

class Comment {
    public User user;
    @SerializedName("book")
    public int bookId;
    public String comment;
    public String time;
}
