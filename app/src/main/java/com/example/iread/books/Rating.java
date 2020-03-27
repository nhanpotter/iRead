package com.example.iread.books;

import com.google.gson.annotations.SerializedName;

public class Rating {
    @SerializedName("book")
    public int bookId;
    public float rating;
}
