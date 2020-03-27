package com.example.iread.di;

import com.example.iread.MainActivity;
import com.example.iread.account.LoginFragment;
<<<<<<< HEAD
<<<<<<< HEAD
import com.example.iread.books.BooksFragment;
=======
<<<<<<< Updated upstream
=======
import com.example.iread.books.BookDetailsFragment;
import com.example.iread.books.BooksFragment;
>>>>>>> Stashed changes
>>>>>>> Connect and share data between BooksList and BookDetails
=======
import com.example.iread.books.BooksFragment;
>>>>>>> Connect and Share data between BookList and BookDetail
import com.example.iread.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {NetworkModule.class})
@Singleton
public interface ApplicationComponent {
    void inject(LoginFragment loginFragment);
    void inject(BooksFragment booksFragment);
<<<<<<< HEAD
    void inject(BookDetailsFragment bookDetailsFragment);
=======
>>>>>>> Connect and Share data between BookList and BookDetail
}
