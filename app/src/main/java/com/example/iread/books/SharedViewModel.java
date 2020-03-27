package com.example.iread.books;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Book> selectedBook = new MutableLiveData<Book>();

    public void selectBook(Book book) {
        selectedBook.setValue(book);
    }

    public LiveData<Book> getSelectedBook() {
        return selectedBook;
    }
}
