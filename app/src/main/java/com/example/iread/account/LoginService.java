package com.example.iread.account;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("auth/token/login/")
    Single<LoginResponse> login(@Body Login login);

    @POST("auth/users/")
    Single<SignUpResponse> signUp(@Body PostUser user);

    @POST("auth/users/reset_password/")
    Single<Email> resetPassword(@Body Email email);
}