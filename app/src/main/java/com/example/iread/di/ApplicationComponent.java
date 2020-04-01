package com.example.iread.di;

import com.example.iread.MainActivity;
import com.example.iread.account.AccountFragment;
import com.example.iread.account.LoginFragment;
import com.example.iread.account.SignupFragment;
import com.example.iread.books.BookDetailsFragment;
import com.example.iread.books.BooksFragment;

import com.example.iread.books.CommentFragment;
import com.example.iread.books.RatingFragment;
import com.example.iread.di.module.NetworkModule;
import com.example.iread.home.HomeFragment;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {NetworkModule.class})
@Singleton
public interface ApplicationComponent {
    void inject(LoginFragment loginFragment);
    void inject(BooksFragment booksFragment);
    void inject(AccountFragment accountFragment);
    void inject(BookDetailsFragment bookDetailsFragment);
    void inject(CommentFragment commentFragment);
    void inject(SignupFragment signupFragment);
    void inject(RatingFragment ratingFragment);
    void inject(HomeFragment homeFragment);
}
