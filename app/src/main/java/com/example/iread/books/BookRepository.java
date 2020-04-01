package com.example.iread.books;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;

@Singleton
public class BookRepository {
    BookService bookService;

    @Inject
    public BookRepository(BookService bookService) {
        this.bookService = bookService;
    }

    public Single<List<Book>> getBooksList() {
        return bookService.getBooksList();
    }

    public Single<Book> getBookDetail(int id) {
        return bookService.getBookDetail(id);
    }

    public Single<List<Book>> getRecommendList() {
        return bookService.getRecommendList();
    }
}
