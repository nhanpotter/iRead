package com.example.iread.books;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BookDetailsViewModel extends ViewModel {
    private final BookRepository bookRepository;

    @Inject
    public BookDetailsViewModel(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Initialized
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Boolean> mutableProgress = new MutableLiveData<>();
    public LiveData<Boolean> progress = mutableProgress;
    private MutableLiveData<Book> mutableBook = new MutableLiveData<>();
    public LiveData<Book> book = mutableBook;
    private MutableLiveData<String> mutableError = new MutableLiveData<>();
    public LiveData<String> error = mutableError;

    public void getBook(int id) {
        Single<Book> bookObservable = bookRepository.getBookDetail(id);
        compositeDisposable.add(bookObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mutableProgress.setValue(true);
                })
                .doFinally(() -> {
                    mutableProgress.setValue(false);
                })
                .subscribe(data -> {
                    mutableBook.setValue(data);
                }, error -> {
                    mutableError.setValue("Unable to get book");
                    throw(error);
                })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
