package com.example.iread.di.module;

import com.example.iread.account.AuthService;
import com.example.iread.account.LoginService;
import com.example.iread.account.RequestAuthenticator;
import com.example.iread.books.BookService;
<<<<<<< HEAD
import com.example.iread.books.FeedbackService;
=======
>>>>>>> Connect and Share data between BookList and BookDetail

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class NetworkModule {
    public static final String baseUrl = "https://iread-server.herokuapp.com";

    @Singleton
    @Provides
    public Retrofit getRetrofit(RequestAuthenticator requestAuthenticator) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(requestAuthenticator)
                .addInterceptor(interceptor)
                .build();

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit getRetrofitWithoutInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    public LoginService provideLoginService() {
        return getRetrofitWithoutInterceptor().create(LoginService.class);
    }

    @Provides
    @Singleton
    public AuthService provideAuthService(RequestAuthenticator requestAuthenticator) {
        return getRetrofit(requestAuthenticator).create(AuthService.class);
    }

    @Provides
    @Singleton
    public BookService provideBookService(RequestAuthenticator requestAuthenticator) {
        return getRetrofit(requestAuthenticator).create(BookService.class);
<<<<<<< HEAD
    }

    @Provides
    @Singleton
    public FeedbackService provideFeedbackService(RequestAuthenticator requestAuthenticator) {
        return getRetrofit(requestAuthenticator).create(FeedbackService.class);
=======
>>>>>>> Connect and Share data between BookList and BookDetail
    }
}
