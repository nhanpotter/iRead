package com.example.iread.books;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookService {

    @GET("book/")
    Single<List<Book>> getBooksList();

    @GET("book/{id}/")
    Single<Book> getBookDetail(@Path("id") int id);

    @GET("book/get/recommend/")
    Single<List<Book>> getRecommendList();
}
