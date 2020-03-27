package com.example.iread.account;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthService {
    @GET("auth/users/me/")
    Single<User> getUserInfo();

    @POST("auth/token/logout")
    Completable logout();
}
