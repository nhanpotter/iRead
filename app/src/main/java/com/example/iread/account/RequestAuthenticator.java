package com.example.iread.account;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestAuthenticator implements Interceptor {
    private AuthHolder authHolder;

    @Inject
    public RequestAuthenticator(AuthHolder authHolder) {
        this.authHolder = authHolder;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        String authorization = authHolder.getAuthorization();
        Request original = chain.request();
        if (authorization != null) {
            Request request = original.newBuilder()
                    .header("Authorization", authorization)
                    .build();
            return chain.proceed(request);
        } else {
            return chain.proceed(original);
        }
    }
}